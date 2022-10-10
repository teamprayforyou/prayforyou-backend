package net.prayforyou.backend.application.event

import net.prayforyou.backend.application.battle.GetBattlePositionService
import net.prayforyou.backend.domain.battle.BattlePlace
import net.prayforyou.backend.domain.clan.enums.ClanLevel
import net.prayforyou.backend.domain.event.Event
import net.prayforyou.backend.domain.match.ClanMatch
import net.prayforyou.backend.domain.match.MatchUser
import net.prayforyou.backend.domain.user.User
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
import kotlin.math.round

@Service
class EventService(
    private val eventProvider: EventProvider,
    private val userJsonProvider: UserJsonProvider,
    private val userRepository: UserRepository,
    private val clanRepository: ClanRepository,
    private val clanMatchRepository: ClanMatchRepository,
    private val clanMatchUserRepository: ClanMatchUserRepository,
    private val getBattlePositionService: GetBattlePositionService,
    private val battlePlaceRepository: BattlePlaceRepository
) {

    data class UserInfo(
        val userNexonId: Int,
        val userNickName: String,
        val targetUserNexonId: Int,
        val targetUserNickName: String
    )

    data class Coordinate(
        val x: Double,
        val y: Double
    )

    @Scheduled(fixedDelay= 200000)
    fun process() {
        val findTodoEvents = eventProvider.findTodoEvents()
        val findTodoUserJson = userJsonProvider.findTodoEvents()

        if (findTodoEvents.isEmpty()) {
            return
        }

        findTodoUserJson.forEach {
            for (userResult in it.userJson.resultClanUserList!!) {
                val findClan = clanRepository.findByClanId(it.clanId)
                if (userRepository.findByUserNexonId(userResult.user_nexon_sn!!) == null) {
                    try {
                        val initialUser = User.initialUser(findClan!!, userResult.user_nexon_sn, userResult.user_nick!!)
                        userRepository.save(initialUser)
                    }
                    catch (_: Exception) {
                        continue
                    }
                } else {
                    // 이미 존재하는 유저가 클랜이 변경되었다면, 클랜을 바꾼다
                    val findUser = userRepository.findByUserNexonId(userResult.user_nexon_sn)
                    findUser?.clanId = findClan
                }
            }
        }

        if (findTodoEvents.isEmpty()) {
            return
        }

        val eventList: MutableList<Event> = mutableListOf()

        // 클랜 매치 생성
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
                    findTodoEvent.matchJson.parseData.MatchData!!.M_CLAN_match_time!!
                )
            } else if (findTodoEvent.matchJson.matchResultDataInfo.red_result.equals("lose"))  {
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
                    findTodoEvent.matchJson.parseData.MatchData!!.M_CLAN_match_time!!
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
                    findTodoEvent.matchJson.parseData.MatchData!!.M_CLAN_match_time!!
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
                    findTodoEvent.matchJson.parseData.MatchData!!.M_CLAN_match_time!!
                )
            }

            clanMatchRepository.save(clanMatch!!)

        }



        for (event in eventList) {

            val usersList = event.battleLogJson.battleLog!!.map { UserInfo(it.user_nexon_sn!!, it.user_nick!!, it.target_user_nexon_sn!!, it.target_user_nick!!) }.distinct()
                .distinctBy { it.targetUserNickName }

            val usersList2 = event.battleLogJson.battleLog!!.map { UserInfo(it.user_nexon_sn!!, it.user_nick!!, it.target_user_nexon_sn!!, it.target_user_nick!!) }.distinct()
                .distinctBy { it.userNickName }


            val map = usersList.map { Pair(it.userNexonId, it.userNickName) }
            val map1 = usersList2.map { Pair(it.targetUserNexonId, it.targetUserNickName) }


            val userNexonIdList = mutableListOf<Int>()
            val userNickNameList = mutableListOf<String>()

            try {
                for (i in 0..usersList.lastIndex) {
                    userNexonIdList.add(usersList2.get(i).userNexonId)
                    userNexonIdList.add(usersList.get(i).targetUserNexonId)
                }

                for (i in 0..usersList.lastIndex) {
                    userNickNameList.add(usersList2.get(i).userNickName)
                    userNickNameList.add(usersList.get(i).targetUserNickName)
                }
            } catch (e: Exception) {
            }

            for (i in 0..userNickNameList.lastIndex) {
                var killCount =
                    event.battleLogJson.battleLog!!.count { it.event_type == "kill" && it.user_nexon_sn == userNexonIdList.get(i) }
                var deathCount =
                    event.battleLogJson.battleLog!!.count { it.event_type == "death" && it.user_nexon_sn == userNexonIdList.get(i) }

                var coordinateKill =
                    event.battleLogJson.battleLog!!.filter { it.event_type == "kill" && it.user_nexon_sn == userNexonIdList.get(i) }
                        .map { Coordinate(it.kill_x!!, it.kill_y!!) }

                var coordinateDeath =
                    event.battleLogJson.battleLog!!.filter { it.event_type == "death" && it.user_nexon_sn == userNexonIdList.get(i) }
                        .map { Coordinate(it.death_x!!, it.death_y!!) }

                var ripleCount =
                    event.battleLogJson.battleLog!!.count { it.weapon == "riple" && it.user_nexon_sn == userNexonIdList.get(i) }

                var sniperCount =
                    event.battleLogJson.battleLog!!.count { it.weapon == "sniper" && it.user_nexon_sn == userNexonIdList.get(i) }

                if (killCount == 0 && deathCount == 0) {
                    killCount =
                        event.battleLogJson.battleLog!!.count { it.target_event_type == "kill" && it.target_user_nexon_sn == userNexonIdList.get(i) }
                    deathCount =
                        event.battleLogJson.battleLog!!.count { it.target_event_type == "death" && it.target_user_nexon_sn == userNexonIdList.get(i) }
                    coordinateKill =
                        event.battleLogJson.battleLog!!.filter { it.event_type == "kill" && it.target_user_nexon_sn == userNexonIdList.get(i) }
                            .map { Coordinate(it.kill_x!!, it.kill_y!!) }

                    coordinateDeath =
                        event.battleLogJson.battleLog!!.filter { it.event_type == "death" && it.target_user_nexon_sn == userNexonIdList.get(i) }
                            .map { Coordinate(it.death_x!!, it.death_y!!) }

                    ripleCount =
                        event.battleLogJson.battleLog!!.count { it.target_weapon == "riple" && it.target_user_nexon_sn == userNexonIdList.get(i) }

                    sniperCount =
                        event.battleLogJson.battleLog!!.count { it.target_weapon == "sniper" && it.target_user_nexon_sn == userNexonIdList.get(i) }

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
                    find = event.battleLogJson.battleLog!!.find { it.target_user_nexon_sn == userNexonIdList.get(i)}
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

                            val battlePlace = battlePlaceRepository.save(BattlePlace(null, findUser, battlePosition))
                            battlePlace.updateKill()
                        }

                        for (deathCord in coordinateDeath) {
                            val battlePosition =
                                getBattlePositionService.getBattlePositionByXandY(deathCord.x, deathCord.y)

                            val battlePlace = battlePlaceRepository.save(BattlePlace(null, findUser, battlePosition))
                            battlePlace.updateDeath()
                        }

                    } else {
                        val createUser =
                            User(
                                null,
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

                            val battlePlace = battlePlaceRepository.save(BattlePlace(null, createUser, battlePosition))
                            battlePlace.updateKill()
                        }

                        for (deathCord in coordinateDeath) {
                            val battlePosition =
                                getBattlePositionService.getBattlePositionByXandY(deathCord.x, deathCord.y)

                            val battlePlace = battlePlaceRepository.save(BattlePlace(null, createUser, battlePosition))
                            battlePlace.updateDeath()
                        }

                    }

                    val findAllClan = clanRepository.findAll()
                    val sortedAClan = findAllClan.sortedBy { it.score }.stream().limit((findAllClan.size / 2).toLong())
                    for (sortedClan in sortedAClan) {
                        sortedClan.clanLevel = ClanLevel.B
                    }
                    val sortedBClan = findAllClan.sortedBy { it.score }.reversed().stream().limit((findAllClan.size / 2).toLong())
                    for (sortedClan in sortedBClan) {
                        sortedClan.clanLevel = ClanLevel.A

                    }

                }
            }

        }

        findTodoEvents.forEach {
            it.todo = false
        }

        findTodoUserJson.forEach {
            it.todo = false
        }
    }
}
