package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.match.MatchService
import net.prayforyou.backend.application.user.UserService
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.response.BlueTeam
import net.prayforyou.backend.presenter.response.MatchResponse
import net.prayforyou.backend.presenter.response.RedTeam
import net.prayforyou.backend.presenter.response.SearchUserResponse
import net.prayforyou.backend.presenter.response.User
import net.prayforyou.backend.presenter.response.UserRankingResponse
import net.prayforyou.backend.presenter.response.UserResponse
import org.springframework.data.domain.Page
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
        val rank = userService.findAll().sortedBy { it.score }.indexOf(userService.getUser(userNexonId)) + 1
        from.ranking = rank
        return from
    }

    @GetMapping("/search")
    fun searchUser(
        @RequestParam("nickname") nickname: String
    ): CommonResponse<List<SearchUserResponse>> =
        CommonResponse.convert(
            userService.searchByNickname(nickname)
                .map { SearchUserResponse.convert(it) }
        )

    @GetMapping("/ranking")
    fun userRanking(pageable: Pageable): CommonResponse<Page<UserRankingResponse>> {
        return CommonResponse.convert(userService.getUserRankingByPaging(pageable))
    }

    @GetMapping("/match")
    fun getUserMatch(
        @RequestParam("userNexonId") userNexonId: Long,
        pageable: Pageable
    ): CommonResponse<MutableList<MatchResponse>> {
        val matchResponse: MutableList<MatchResponse> = mutableListOf()
        val matchList = matchService.getMatchDataByUserNexonId(userNexonId, pageable)
        for (match in matchList) {
            val redUsers = matchService.getMatchUsers(match.matchId, match.redClan.id!!)
            val blueUsers = matchService.getMatchUsers(match.matchId, match.blueClan.id!!)
            val plusUserScore = (redUsers.find { it.user.userNexonId.toLong() == userNexonId }?.plusScore
                ?: blueUsers.find { it.user.userNexonId.toLong() == userNexonId }?.plusScore)
            matchResponse.add(
                MatchResponse(
                    gameProgressTime = match.totalMatchTime,
                    isWin = match.isRedTeamWin,
                    lastGameDay = match.matchTimeKorean,
                    addScore = plusUserScore!!,
                    matchId = match.matchId,
                    redTeam = RedTeam(
                        match.redClan.clanId,
                        match.redClan.score.toInt(),
                        redUsers.map { User(it.user.nickname!!, it.user.userNexonId.toLong()) }.toList(),
                        match.redClan.clanLevel,
                        match.redClan.clanName
                    ),
                    blueTeam = BlueTeam(
                        match.blueClan.clanId,
                        match.blueClan.score.toInt(),
                        blueUsers.map { User(it.user.nickname!!, it.user.userNexonId.toLong()) }.toList(),
                        match.blueClan.clanLevel,
                        match.blueClan.clanName
                    ),
                    isLast = matchList.isLast,
                    totalPages = matchList.totalPages
                )
            )
        }

        return CommonResponse.convert(matchResponse)
    }
}