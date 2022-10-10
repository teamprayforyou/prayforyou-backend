package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.match.MatchService
import net.prayforyou.backend.application.user.UserService
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.global.common.PageResponse
import net.prayforyou.backend.global.util.DateUtil
import net.prayforyou.backend.presenter.response.BlueTeam
import net.prayforyou.backend.presenter.response.MatchResponse
import net.prayforyou.backend.presenter.response.RedTeam
import net.prayforyou.backend.presenter.response.SearchUserResponse
import net.prayforyou.backend.presenter.response.User
import net.prayforyou.backend.presenter.response.UserRankingResponse
import net.prayforyou.backend.presenter.response.UserResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val matchService: MatchService
) {

    @GetMapping("/{userNexonId}")
    fun getUser(@PathVariable userNexonId: Long): UserResponse {
        val from = UserResponse.from(userService.getUser(userNexonId)!!)
        val rank = userService.findAll().sortedBy { it.score }.reversed().indexOf(userService.getUser(userNexonId)) + 1
        from.ranking = rank
        return from
    }

    @GetMapping("/search")
    fun searchUser(
        @RequestParam("nickname") nickname: String
    ): CommonResponse<List<SearchUserResponse>> {
        val user = userService.searchByNickname(nickname)
        val userFilter = user.filter { x -> x.gameCount != 0 }
        return CommonResponse.convert(userFilter.map { SearchUserResponse.convert(it) })
    }

    @GetMapping("/ranking")
    fun userRanking(pageable: Pageable): PageResponse<MutableList<UserRankingResponse>> {
        val userRankingResponse: MutableList<UserRankingResponse> = mutableListOf()
        val users = userService.getUserRankingByPaging(pageable)
        for (user in users) {
            userRankingResponse.add(UserRankingResponse.from(user))
        }

        return PageResponse.convert(userRankingResponse, users.isLast, users.totalPages)
    }

    @GetMapping("/match")
    fun getUserMatch(
        @RequestParam("userNexonId") userNexonId: Long,
        pageable: Pageable
    ): PageResponse<MutableList<MatchResponse>> {
        val matchResponse: MutableList<MatchResponse> = mutableListOf()
        val matchList = matchService.getMatchDataByUserNexonId(userNexonId, pageable)
        for (match in matchList) {
            val redUsers = matchService.getMatchUsers(match.matchId, match.redClan.id!!)
            val blueUsers = matchService.getMatchUsers(match.matchId, match.blueClan.id!!)
            val plusUserScore = (redUsers.find { it.user.userNexonId.toLong() == userNexonId }?.plusScore
                ?: blueUsers.find { it.user.userNexonId.toLong() == userNexonId }?.plusScore)

            val matchUser = matchService.getMatchUser(userService.getUser(userNexonId)!!, match)

            var isWin = false
            if (match.redClan.clanId == matchUser.playClan.clanId) {
                isWin = match.isRedTeamWin
            } else if (match.blueClan.clanId == matchUser.playClan.clanId) {
                isWin = !match.isRedTeamWin
            }

            matchResponse.add(
                MatchResponse(
                    gameProgressTime = match.totalMatchTime,
                    isWin = isWin,
                    lastGameDay = DateUtil.calculateTime(DateUtil.toDate(match.matchStartTime))!!,
                    addScore = plusUserScore!!,
                    matchId = match.matchId.toString(),
                    isDraw = match.isDraw,
                    redTeam = RedTeam(
                        match.redClan.clanId,
                        match.redClan.score.toInt(),
                        redUsers.map {
                            User(
                                it.user.nickname!!,
                                it.user.userNexonId.toLong(),
                                it.killCount.toLong(),
                                it.deathCount.toLong()
                            )
                        }.toList(),
                        match.redClan.clanLevel.levelName,
                        match.redClan.clanNickname
                    ),
                    blueTeam = BlueTeam(
                        match.blueClan.clanId,
                        match.blueClan.score.toInt(),
                        blueUsers.map {
                            User(
                                it.user.nickname!!,
                                it.user.userNexonId.toLong(),
                                it.killCount.toLong(),
                                it.deathCount.toLong()
                            )
                        }.toList(),
                        match.blueClan.clanLevel.levelName,
                        match.blueClan.clanNickname
                    )
                )
            )
        }

        return PageResponse.convert(matchResponse, matchList.isLast, matchList.totalPages)
    }
}
