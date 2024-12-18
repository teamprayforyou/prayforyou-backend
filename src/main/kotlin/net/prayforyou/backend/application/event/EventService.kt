package net.prayforyou.backend.application.event

import net.prayforyou.backend.domain.clan.Clan
import net.prayforyou.backend.domain.clan.enums.ClanLevel
import net.prayforyou.backend.domain.event.Event
import net.prayforyou.backend.domain.match.ClanMatch
import net.prayforyou.backend.domain.match.MatchUser
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.UserJson
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.event.EventProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserJsonProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.clan.ClanMatchRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.clan.ClanMatchUserRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.clan.ClanRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import kotlin.math.round

@Service
@Transactional
class EventService(
    private val eventProvider: EventProvider,
    private val userJsonProvider: UserJsonProvider,
    private val userRepository: UserRepository,
    private val clanRepository: ClanRepository,
    private val clanMatchRepository: ClanMatchRepository,
    private val clanMatchUserRepository: ClanMatchUserRepository,
    private val entityManager: EntityManager
) {

    data class UserInfo(
        val userNexonId: Int,
        val userNickName: String,
        val targetUserNexonId: Int,
        val targetUserNickName: String
    )

//    @Scheduled(fixedDelay = 1000 * 60 * 20)
    fun process() {
        // 처리할 데이터들 setup
        val findTodoEvents = eventProvider.findTodoEvents()
        val findTodoUserJson = userJsonProvider.findTodoEvents().filter { it.userJson.resultClanUserList != null }
        val findAllUser = userRepository.findAll()
        val findAllClan = clanRepository.findAll()

        // 배치 Task가 없다면 종료
        if (findTodoEvents.isEmpty()) {
            println("작업할 task 없음 종료")
            return
        }

        saveGameNotPlayUser(findTodoUserJson, findAllUser, findAllClan)

        entityManager.flush()
        entityManager.clear()

        if (findTodoEvents.isEmpty()) {
            println("작업할 task 없음 종료")
            return
        }

        val p4uGameList: MutableList<Event> = mutableListOf()

        // 클랜 매치 생성
        saveClanMatch(findTodoEvents, p4uGameList)

        processGame(p4uGameList)

        finishBatchTask()
        println("배치 1회 종료")
    }

    private fun finishBatchTask(
    ) {
        val findTodoEvent = eventProvider.findTodoEvents()
        val findTodoUserJson = userJsonProvider.findTodoEvents()

        findTodoEvent.forEach {
            it.todo = false
        }

        findTodoUserJson.forEach {
            it.todo = false
        }
    }

    private fun processGame(eventList: MutableList<Event>) {
        for (event in eventList) {

            val usersList = event.battleLogJson.battleLog!!.map {
                UserInfo(
                    it.user_nexon_sn!!,
                    it.user_nick!!,
                    it.target_user_nexon_sn!!,
                    it.target_user_nick!!
                )
            }.distinct()
                .distinctBy { it.targetUserNickName }.toMutableList()

            val usersList2 = event.battleLogJson.battleLog!!.map {
                UserInfo(
                    it.user_nexon_sn!!,
                    it.user_nick!!,
                    it.target_user_nexon_sn!!,
                    it.target_user_nick!!
                )
            }.distinct()
                .distinctBy { it.userNickName }.toMutableList()

            usersList2.removeIf { it.userNexonId == 0 }
            usersList.removeIf { it.targetUserNexonId == 0 }
            usersList2.removeIf { it.userNickName == "" }
            usersList.removeIf { it.targetUserNickName == "" }


            val userNexonIdList = mutableListOf<Int>()
            val userNickNameList = mutableListOf<String>()

            val index = if (usersList.lastIndex < usersList2.lastIndex) {
                usersList2.lastIndex
            } else {
                usersList.lastIndex
            }

            if (usersList.size != 5 || usersList2.size != 5) {
                continue
            }

            for (i in 0..index) {
                userNexonIdList.add(usersList2.get(i).userNexonId)
                userNexonIdList.add(usersList.get(i).targetUserNexonId)
            }

            for (i in 0..index) {
                userNickNameList.add(usersList2.get(i).userNickName)
                userNickNameList.add(usersList.get(i).targetUserNickName)
            }

            for (i in 0..userNickNameList.lastIndex) {
                var killCount =
                    event.battleLogJson.battleLog!!.count {
                        it.event_type == "kill" && it.user_nexon_sn == userNexonIdList.get(
                            i
                        )
                    }
                var deathCount =
                    event.battleLogJson.battleLog!!.count {
                        it.event_type == "death" && it.user_nexon_sn == userNexonIdList.get(
                            i
                        )
                    }

                var ripleCount =
                    event.battleLogJson.battleLog!!.count {
                        it.weapon == "riple" && it.user_nexon_sn == userNexonIdList.get(
                            i
                        )
                    }

                var sniperCount =
                    event.battleLogJson.battleLog!!.count {
                        it.weapon == "sniper" && it.user_nexon_sn == userNexonIdList.get(
                            i
                        )
                    }

                if (killCount == 0 && deathCount == 0) {
                    killCount =
                        event.battleLogJson.battleLog!!.count {
                            it.target_event_type == "kill" && it.target_user_nexon_sn == userNexonIdList.get(
                                i
                            )
                        }
                    deathCount =
                        event.battleLogJson.battleLog!!.count {
                            it.target_event_type == "death" && it.target_user_nexon_sn == userNexonIdList.get(
                                i
                            )
                        }
                    ripleCount =
                        event.battleLogJson.battleLog!!.count {
                            it.target_weapon == "riple" && it.target_user_nexon_sn == userNexonIdList.get(
                                i
                            )
                        }

                    sniperCount =
                        event.battleLogJson.battleLog!!.count {
                            it.target_weapon == "sniper" && it.target_user_nexon_sn == userNexonIdList.get(
                                i
                            )
                        }

                    if (killCount == 0 && deathCount == 0) {
                        continue
                    }
                }


                var killDeath: Double = killCount * 100 / (killCount + deathCount).toDouble()
                killDeath = round(killDeath * 100) / 100
                var score = 0

                val winClanNo = event.matchJson.matchResultDataInfo!!.win_clan_no

                val winTeamNo = event.battleLogJson.teamList!!.find { it.clan_no == winClanNo }!!.team_no
                val winTeamClanNo = event.battleLogJson.teamList!!.find { it.clan_no == winClanNo }!!.clan_no

                var isTargetUser = false
                var find =
                    event.battleLogJson.battleLog!!.find { it.user_nexon_sn == userNexonIdList.get(i) }
                if (find == null) {
                    find =
                        event.battleLogJson.battleLog!!.find { it.target_user_nexon_sn == userNexonIdList.get(i) }
                    isTargetUser = true
                    if (find == null) {
                        continue
                    }
                }
                val playerTeamNo: String
                val loseTeamNo = event.battleLogJson.teamList!!.filterNot { it.team_no == winTeamNo }.last().clan_no

                var isWinUser = false

                if (isTargetUser) {
                    if (find.target_team_no == winTeamNo) {
                        isWinUser = true
                    }
                } else {
                    if (find.team_no == winTeamNo) {
                        isWinUser = true
                    }
                }

                var isSniper = false

                val weaponName: String = if (ripleCount >= sniperCount) {
                    "라이플"
                } else {
                    "스나이퍼"
                }

                if (weaponName == "스나이퍼") {
                    isSniper = true
                }

                if (isWinUser) {
                    score += 10
                    playerTeamNo = winTeamClanNo!!
                } else {
                    playerTeamNo = loseTeamNo!!
                }

                println(userNexonIdList.get(i))
                val findUser = userRepository.findByUserNexonId(userNexonIdList.get(i))

                var winlosePercent = 0.0

                var winCount = 0
                var loseCount = 1
                if (isWinUser) {
                    winlosePercent = 100.0
                    winCount++
                    loseCount = 0
                }
                println("===")
                println(event.matchKey.toLong())
                println("===")
                val findClanMatch =
                    clanMatchRepository.findByMatchId(event.matchKey.toLong())

                val playerTeam = clanRepository.findByClanId(playerTeamNo)

                if (userNickNameList.get(i).isNotEmpty()) {
                    if (findUser != null) {
                        if (winCount == 1) {
                            findUser.increaseWinCount()
                        }
                        findUser.increaseKillCount(killCount)
                        findUser.increaseDeathCount(deathCount)
                        findUser.updateNickname(userNickNameList.get(i))
                        findUser.increaseGameCount()
                        findUser.updateWinLosePercent()
                        findUser.setPrimaryUseGun(ripleCount, sniperCount)
                        findUser.updateKillDeath()
                        findUser.updateKillPerGame()
                        findUser.calculateScore()
                        clanMatchUserRepository.save(
                            MatchUser(
                                null,
                                findUser,
                                findClanMatch,
                                isSniper,
                                killCount,
                                deathCount,
                                killDeath,
                                playerTeam!!,
                                (killCount * 2 - deathCount * 1 + winCount * 10 - loseCount * 10)
                            )
                        )



                    } else {
                        val createUser =
                            User.from(
                                userNickNameList.get(i),
                                userNexonIdList.get(i),
                                null,
                                0L,
                                killDeath,
                                killCount,
                                deathCount,
                                1,
                                killCount.toDouble(),
                                weaponName,
                                winlosePercent,
                                winCount
                            )
                        createUser.calculateScore()

                        userRepository.save(createUser)

                        clanMatchUserRepository.save(
                            MatchUser(
                                null,
                                createUser,
                                findClanMatch,
                                isSniper,
                                killCount,
                                deathCount,
                                killDeath,
                                playerTeam!!,
                                (killCount * 2 - deathCount * 1 + winCount * 10 - loseCount * 10)
                            )
                        )

                    }

                    val findAllClan = clanRepository.findAll()
                    val sortedAClan =
                        findAllClan.sortedBy { it.score }.stream().limit((findAllClan.size / 2).toLong())
                    for (sortedClan in sortedAClan) {
                        sortedClan.clanLevel = ClanLevel.B
                    }
                    val sortedBClan =
                        findAllClan.sortedBy { it.score }.reversed().stream().limit((findAllClan.size / 2).toLong())
                    for (sortedClan in sortedBClan) {
                        sortedClan.clanLevel = ClanLevel.A

                    }

                }
            }

        }
    }

    private fun saveClanMatch(
        findTodoEvents: List<Event>,
        eventList: MutableList<Event>
    ) {
        for (findTodoEvent in findTodoEvents) {
            val redClan =
                (clanRepository.findByClanId(findTodoEvent.matchJson.matchResultDataInfo.red_clan_no!!)
                    ?: continue)
            val blueClan =
                (clanRepository.findByClanId(findTodoEvent.matchJson.matchResultDataInfo.blue_clan_no!!)
                    ?: continue)
            eventList.add(findTodoEvent)
            var isRedTeamWin = false
            var clanMatch: ClanMatch? = null
            // 레드팀이 졌으면 - 블루팀 승리
            if (findTodoEvent.matchJson.matchResultDataInfo.red_result.equals("win")) {
                isRedTeamWin = true

                redClan.updateWinLoseCount(1, 0)
                redClan.calculateWinLosePercent()
                redClan.calculateScore()

                clanMatch = ClanMatch(
                    null,
                    findTodoEvent.matchKey.toLong(),
                    redClan,
                    blueClan,
                    isRedTeamWin,
                    0,
                    findTodoEvent.matchTime,
                    findTodoEvent.battleLogJson.battleLog!!.last().event_time!!,
                    findTodoEvent.matchJson.matchResultDataInfo.match_time!!,
                    false
                )
            } else if (findTodoEvent.matchJson.matchResultDataInfo.red_result.equals("lose")) {
                isRedTeamWin = false

                redClan.updateWinLoseCount(0, 1)
                redClan.calculateWinLosePercent()
                redClan.calculateScore()

                clanMatch = ClanMatch(
                    null,
                    findTodoEvent.matchKey.toLong(),
                    redClan,
                    blueClan,
                    isRedTeamWin,
                    0,
                    findTodoEvent.matchTime,
                    findTodoEvent.battleLogJson.battleLog!!.last().event_time!!,
                    findTodoEvent.matchJson.matchResultDataInfo.match_time!!,
                    false
                )
            }

            if (findTodoEvent.matchJson.matchResultDataInfo.blue_result.equals("win")) {
                isRedTeamWin = false

                blueClan.updateWinLoseCount(1, 0)
                blueClan.calculateWinLosePercent()
                blueClan.calculateScore()

                clanMatch = ClanMatch(
                    null,
                    findTodoEvent.matchKey.toLong(),
                    redClan,
                    blueClan,
                    isRedTeamWin,
                    0,
                    findTodoEvent.matchTime,
                    findTodoEvent.battleLogJson.battleLog!!.last().event_time!!,
                    findTodoEvent.matchJson.matchResultDataInfo.match_time!!,
                    false
                )
            } else if (findTodoEvent.matchJson.matchResultDataInfo.blue_result.equals("lose")) {
                isRedTeamWin = true

                blueClan.updateWinLoseCount(0, 1)
                blueClan.calculateWinLosePercent()
                blueClan.calculateScore()

                clanMatch = ClanMatch(
                    null,
                    findTodoEvent.matchKey.toLong(),
                    redClan,
                    blueClan,
                    isRedTeamWin,
                    0,
                    findTodoEvent.matchTime,
                    findTodoEvent.battleLogJson.battleLog!!.last().event_time!!,
                    findTodoEvent.matchJson.matchResultDataInfo.match_time!!,
                    false
                )
            }

            if (findTodoEvent.matchJson.matchResultDataInfo.blue_result.equals("draw")) {
                isRedTeamWin = false

                redClan.updateWinLoseCount(0, 0)
                redClan.calculateWinLosePercent()
                redClan.calculateScore()
                blueClan.updateWinLoseCount(0, 0)
                blueClan.calculateWinLosePercent()
                blueClan.calculateScore()

                clanMatch = ClanMatch(
                    null,
                    findTodoEvent.matchKey.toLong(),
                    redClan,
                    blueClan,
                    isRedTeamWin,
                    0,
                    findTodoEvent.matchTime,
                    findTodoEvent.battleLogJson.battleLog!!.last().event_time!!,
                    findTodoEvent.matchJson.matchResultDataInfo.match_time!!,
                    true
                )
            }

            clanMatchRepository.save(clanMatch!!)

        }
    }

    private fun saveGameNotPlayUser(
        findTodoUserJson: List<UserJson>,
        findAllUserNexonId: MutableList<User>,
        findAllClan: List<Clan>
    ) {

        val userNexonIdList = findAllUserNexonId.map { it.userNexonId }

        val saveUserList: MutableSet<User> = mutableSetOf()

        if (findTodoUserJson.isNotEmpty()) {
            findTodoUserJson.forEach { todo ->
                // resultClanUserList에서 디비에서 찾아온 userNexonId가 포함되지 않은 리스트 (새로운 유저 nexonId 리스트)
                val newUserList = todo.userJson.resultClanUserList!!.filterNot {
                    userNexonIdList.contains(it.user_nexon_sn)
                }

                // 새로 가입한 유저 찾기
                val newUser =
                    newUserList.filterNot { new ->
                        findAllUserNexonId.map { it.userNexonId }.contains(new.user_nexon_sn)
                    }

                // 새로 가입한 유저 저장하기
                saveUserList.addAll(newUser.map {
                    User.initialUser(
                        findAllClan.find { clan -> todo.clanId == clan.clanId },
                        it.user_nexon_sn!!,
                        it.user_nick!!
                    )
                })
                val oldUser =
                    findAllUserNexonId.filterNot { old ->
                        newUserList.map { it.user_nexon_sn }.contains(old.userNexonId)
                    }
                if (oldUser.isNotEmpty()) {
                    // 원래 있던 유저 클랜 변경되었을 때
                    for (user in oldUser) {
                        val findUserJson = findTodoUserJson.find {
                            it.userJson.resultClanUserList!!.map { user -> user.user_nexon_sn }
                                .contains(user.userNexonId)
                        }

                        if (findUserJson != null) {
                            if (user.clanId?.clanId != findUserJson.clanId) {
                                user.clanId = findAllClan.find { clan -> findUserJson.clanId == clan.clanId }
                                userRepository.save(user)
                            }
                        }
                    }
                }
            }
        }

        if (saveUserList.isNotEmpty()) {
            userRepository.saveAll(saveUserList.distinctBy { it.userNexonId })
        }
    }

}
