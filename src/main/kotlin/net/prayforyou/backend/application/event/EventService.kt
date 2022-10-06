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
@Transactional
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
        val userNickName: String
    )

    data class Coordinate(
        val x: Double,
        val y: Double
    )

    @Scheduled(fixedDelay = 1800000)
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
                    val initialUser = User.initialUser(findClan!!, userResult.user_nexon_sn, userResult.user_nick!!)
                    userRepository.saveAndFlush(initialUser)
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
                (clanRepository.findByClanId(findTodoEvent.matchJson.matchResultDataInfo!!.red_clan_no!!.toLong())
                    ?: continue)
            val blueClan =
                (clanRepository.findByClanId(findTodoEvent.matchJson.matchResultDataInfo!!.blue_clan_no!!.toLong())
                    ?: continue)
            eventList.add(findTodoEvent)
            var isRedTeamWin = false
            var clanMatch: ClanMatch
            if (!findTodoEvent.matchJson.matchResultDataInfo!!.red_result.equals("lose")) {
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
                    (1 * 10 + 0 - 10),
                    findTodoEvent.matchTime,
                    findTodoEvent.battleLogJson.battleLog!!.last().event_time!!,
                    findTodoEvent.matchJson.parseData.MatchData!!.M_CLAN_match_time!!
                )
            } else {
                blueClan.updateWinLoseCount(0, 1)
                blueClan.calculateWinLosePercent()
                blueClan.calculateScore()
                clanMatch = ClanMatch(
                    null,
                    findTodoEvent.matchKey.toLong(),
                    redClan,
                    blueClan,
                    isRedTeamWin,
                    (0 * 10 + 1 - 10),
                    findTodoEvent.matchTime,
                    findTodoEvent.battleLogJson.battleLog!!.last().event_time!!,
                    findTodoEvent.matchJson.parseData.MatchData!!.M_CLAN_match_time!!
                )
            }



            clanMatchRepository.saveAndFlush(clanMatch)

        }

        val usersList = findTodoEvents.map { todo ->
            todo.battleLogJson.battleLog!!.map { UserInfo(it.user_nexon_sn!!, it.user_nick!!) }.distinct()
        }.flatten().distinctBy { it.userNexonId }

        for (event in eventList) {
            for (userInfo in usersList) {
                var killCount =
                    event.battleLogJson.battleLog!!.count { it.event_type == "kill" && it.user_nexon_sn == userInfo.userNexonId }
                var deathCount =
                    event.battleLogJson.battleLog!!.count { it.event_type == "death" && it.user_nexon_sn == userInfo.userNexonId }

                var coordinateKill =
                    event.battleLogJson.battleLog!!.filter { it.event_type == "kill" && it.user_nexon_sn == userInfo.userNexonId }
                        .map { Coordinate(it.kill_x!!, it.kill_y!!) }

                var coordinateDeath =
                    event.battleLogJson.battleLog!!.filter { it.event_type == "death" && it.user_nexon_sn == userInfo.userNexonId }
                        .map { Coordinate(it.death_x!!, it.death_y!!) }

                var ripleCount =
                    event.battleLogJson.battleLog!!.count { it.weapon == "riple" && it.user_nexon_sn == userInfo.userNexonId }

                var sniperCount =
                    event.battleLogJson.battleLog!!.count { it.weapon == "sniper" && it.user_nexon_sn == userInfo.userNexonId }

                if (killCount == 0 && deathCount == 0) {
                    killCount =
                        event.battleLogJson.battleLog!!.count { it.target_event_type == "kill" && it.target_user_nexon_sn == userInfo.userNexonId }
                    deathCount =
                        event.battleLogJson.battleLog!!.count { it.target_event_type == "death" && it.target_user_nexon_sn == userInfo.userNexonId }
                    coordinateKill =
                        event.battleLogJson.battleLog!!.filter { it.event_type == "kill" && it.target_user_nexon_sn == userInfo.userNexonId }
                            .map { Coordinate(it.kill_x!!, it.kill_y!!) }

                    coordinateDeath =
                        event.battleLogJson.battleLog!!.filter { it.event_type == "death" && it.target_user_nexon_sn == userInfo.userNexonId }
                            .map { Coordinate(it.death_x!!, it.death_y!!) }

                    ripleCount =
                        event.battleLogJson.battleLog!!.count { it.weapon == "riple" && it.target_user_nexon_sn == userInfo.userNexonId }

                    sniperCount =
                        event.battleLogJson.battleLog!!.count { it.weapon == "sniper" && it.target_user_nexon_sn == userInfo.userNexonId }

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
                    event.battleLogJson.battleLog!!.find { it.user_nexon_sn == userInfo.userNexonId }
                if (find == null) {
                    find = event.battleLogJson.battleLog!!.find { it.target_user_nexon_sn == userInfo.userNexonId }
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

                val findUser = userRepository.findByUserNexonId(userInfo.userNexonId)

                var winlosePercent = 0.0

                var winCount = 0
                if (isWinUser) {
                    winlosePercent = 100.0
                    winCount++
                }

                val findClanMatch =
                    clanMatchRepository.findByMatchId(event.matchKey.toLong())

                val playerTeam = clanRepository.findByClanId(playerTeamNo.toLong())

                if (userInfo.userNickName.isNotEmpty()) {
                    if (findUser != null) {
                        if (winCount == 1) {
                            findUser.increaseWinCount()
                        }
                        findUser.increaseKillCount(killCount)
                        findUser.increaseDeathCount(deathCount)
                        findUser.updateNickname(userInfo.userNickName)
                        findUser.updateKillDeath(killCount, deathCount)
                        findUser.increaseGameCount()
                        findUser.updateKillPerGame()
                        findUser.updateWinLosePercent()
                        findUser.setPrimaryUseGun(ripleCount, sniperCount)
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
                                (killCount * 5 - deathCount * 3 + winCount * 10 - winCount * 5)
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
                                userInfo.userNickName,
                                userInfo.userNexonId,
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
                                (killCount * 5 - deathCount * 3 + winCount * 10 - winCount * 5)
                            )
                        )
                        userRepository.saveAndFlush(createUser)

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
