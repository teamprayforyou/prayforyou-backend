package net.prayforyou.backend.application.event

import net.prayforyou.backend.application.battle.GetBattlePositionService
import net.prayforyou.backend.domain.battle.BattlePlace
import net.prayforyou.backend.domain.clan.Clan
import net.prayforyou.backend.domain.clan.enums.ClanLevel
import net.prayforyou.backend.domain.event.Event
import net.prayforyou.backend.domain.match.ClanMatch
import net.prayforyou.backend.domain.match.MatchUser
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.UserJson
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.event.EventProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserJsonProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle.BattlePlaceRepository
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
    private val getBattlePositionService: GetBattlePositionService,
    private val battlePlaceRepository: BattlePlaceRepository,
    private val entityManager: EntityManager
) {

    class PlayerList(
        val userNexonId: MutableSet<Int> = mutableSetOf(),
        val userNickName: MutableSet<String> = mutableSetOf(),
    )

    data class Coordinate(
        val x: Double,
        val y: Double
    )

    @Scheduled(fixedDelay = 200000)
    fun process() {
        val findTodoEvents = eventProvider.findTodoEvents()
        val findTodoUserJson = userJsonProvider.findTodoEvents()

        if (findTodoEvents.isEmpty()) {
            return
        }

        val findAllUser = userRepository.findAll()
        val findAllClan = clanRepository.findAll()

        val saveUserList: MutableSet<User> = saveGameNotStartUser(findAllUser, findTodoUserJson, findAllClan)

        userRepository.saveAll(saveUserList.distinctBy { it.userNexonId })

        entityManager.flush()
        entityManager.clear()

        val p4uClanMatchList: MutableList<Event> = mutableListOf()

        // 클랜 매치 생성
        createClanMatchAndFindP4uClanMatch(findTodoEvents, p4uClanMatchList)

        processGame(p4uClanMatchList)

        makeTodoJsonDone()

        println("finish")
        println("finish")
        println("finish")
        println("finish")
        println("finish")
    }

    private fun makeTodoJsonDone(
    ) {
        val findTodoEvents = eventProvider.findTodoEvents()
        val findTodoUserJson = userJsonProvider.findTodoEvents()

        findTodoEvents.forEach {
            it.todo = false
        }

        findTodoUserJson.forEach {
            it.todo = false
        }
    }

    private fun processGame(eventList: MutableList<Event>) {
        for (event in eventList) {
            // 하나의 게임에 참여한 유저들의 정보
            val playersInfo = PlayerList()

            // 배틀로그 json 데이터에서 유저 정보를 가져온다
            event.battleLogJson.battleLog!!.forEach {
                playersInfo.userNexonId.add(it.user_nexon_sn!!)
                playersInfo.userNexonId.add(it.target_user_nexon_sn!!)
                playersInfo.userNickName.add(it.user_nick!!)
                playersInfo.userNickName.add(it.target_user_nick!!)
            }

            // 유저 정보 id 값에 0 또는 스트링 빈값 ("") 들어가는 경우가 있으므로 제거
            playersInfo.userNexonId.removeIf { it == 0 }
            playersInfo.userNickName.removeIf { it == "" }

            val userNexonIdList = mutableListOf<Int>()
            val userNickNameList = mutableListOf<String>()


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

                var coordinateKill =
                    event.battleLogJson.battleLog!!.filter {
                        it.event_type == "kill" && it.user_nexon_sn == userNexonIdList.get(
                            i
                        )
                    }
                        .map { Coordinate(it.kill_x!!, it.kill_y!!) }

                var coordinateDeath =
                    event.battleLogJson.battleLog!!.filter {
                        it.event_type == "death" && it.user_nexon_sn == userNexonIdList.get(
                            i
                        )
                    }
                        .map { Coordinate(it.death_x!!, it.death_y!!) }

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
                    coordinateKill =
                        event.battleLogJson.battleLog!!.filter {
                            it.event_type == "kill" && it.target_user_nexon_sn == userNexonIdList.get(
                                i
                            )
                        }
                            .map { Coordinate(it.kill_x!!, it.kill_y!!) }

                    coordinateDeath =
                        event.battleLogJson.battleLog!!.filter {
                            it.event_type == "death" && it.target_user_nexon_sn == userNexonIdList.get(
                                i
                            )
                        }
                            .map { Coordinate(it.death_x!!, it.death_y!!) }

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

                val findUser = userRepository.findByUserNexonId(userNexonIdList.get(i))

                var winlosePercent = 0.0

                var winCount = 0
                var loseCount = 1
                if (isWinUser) {
                    winlosePercent = 100.0
                    winCount++
                    loseCount = 0
                }

                val findClanMatch =
                    clanMatchRepository.findByMatchId(event.matchKey.toLong())

                val playerTeam = clanRepository.findByClanId(playerTeamNo.toLong())

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

                        for (killCord in coordinateKill) {
                            val battlePosition =
                                getBattlePositionService.getBattlePositionByXandY(killCord.x, killCord.y)

                            val battlePlace =
                                battlePlaceRepository.save(BattlePlace(null, findUser, battlePosition))
                            battlePlace.updateKill()
                        }

                        for (deathCord in coordinateDeath) {
                            val battlePosition =
                                getBattlePositionService.getBattlePositionByXandY(deathCord.x, deathCord.y)

                            val battlePlace =
                                battlePlaceRepository.save(BattlePlace(null, findUser, battlePosition))
                            battlePlace.updateDeath()
                        }

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

                        for (killCord in coordinateKill) {
                            val battlePosition =
                                getBattlePositionService.getBattlePositionByXandY(killCord.x, killCord.y)

                            val battlePlace =
                                battlePlaceRepository.save(BattlePlace(null, createUser, battlePosition))
                            battlePlace.updateKill()
                        }

                        for (deathCord in coordinateDeath) {
                            val battlePosition =
                                getBattlePositionService.getBattlePositionByXandY(deathCord.x, deathCord.y)

                            val battlePlace =
                                battlePlaceRepository.save(BattlePlace(null, createUser, battlePosition))
                            battlePlace.updateDeath()
                        }

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

    private fun createClanMatchAndFindP4uClanMatch(
        findTodoEvents: List<Event>,
        eventList: MutableList<Event>
    ) {
        for (findTodoEvent in findTodoEvents) {
            val redClan =
                (clanRepository.findByClanId(findTodoEvent.matchJson.matchResultDataInfo.red_clan_no!!.toLong())
                    ?: continue)
            val blueClan =
                (clanRepository.findByClanId(findTodoEvent.matchJson.matchResultDataInfo.blue_clan_no!!.toLong())
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
                    findTodoEvent.matchJson.parseData.MatchData!!.M_CLAN_match_time!!,
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
                    findTodoEvent.matchJson.parseData.MatchData!!.M_CLAN_match_time!!,
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
                    findTodoEvent.matchJson.parseData.MatchData!!.M_CLAN_match_time!!,
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
                    findTodoEvent.matchJson.parseData.MatchData!!.M_CLAN_match_time!!,
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
                    findTodoEvent.matchJson.parseData.MatchData!!.M_CLAN_match_time!!,
                    true
                )
            }

            clanMatchRepository.save(clanMatch!!)

        }
    }

    private fun saveGameNotStartUser(
        findAllUserNexonId: MutableList<User>,
        findTodoUserJson: List<UserJson>,
        findAllClan: List<Clan>
    ): MutableSet<User> {
        val userNexonIdList = findAllUserNexonId.map { it.userNexonId }

        val saveUserList: MutableSet<User> = mutableSetOf()
        findTodoUserJson.forEach { todo ->
            // resultClanUserList에서 디비에서 찾아온 userNexonId가 포함되지 않은 리스트 (새로운 유저 nexonId 리스트)
            val newUserList = todo.userJson.resultClanUserList!!.filterNot {
                userNexonIdList.contains(it.user_nexon_sn)
            }

            // 새로 가입한 유저 찾기
            val newUser =
                newUserList.filterNot { new -> findAllUserNexonId.map { it.userNexonId }.contains(new.user_nexon_sn) }

            // 새로 가입한 유저 저장하기
            saveUserList.addAll(newUser.map {
                User.initialUser(
                    findAllClan.find { clan -> todo.clanId == clan.clanId },
                    it.user_nexon_sn!!,
                    it.user_nick!!
                )
            })
            val oldUser =
                findAllUserNexonId.filterNot { old -> newUserList.map { it.user_nexon_sn }.contains(old.userNexonId) }
            if (oldUser.isNotEmpty()) {
                // 원래 있던 유저 클랜 변경되었을 때
                for (user in oldUser) {
                    val findUserJson = findTodoUserJson.find {
                        it.userJson.resultClanUserList!!.map { user -> user.user_nexon_sn }.contains(user.userNexonId)
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
        return saveUserList
    }

}
