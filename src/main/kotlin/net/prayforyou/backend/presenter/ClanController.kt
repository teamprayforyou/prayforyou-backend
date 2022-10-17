package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.clan.ClanService
import net.prayforyou.backend.application.match.MatchService
import net.prayforyou.backend.domain.clan.enums.ClanLevel
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.global.common.PageResponse
import net.prayforyou.backend.global.util.DateUtil
import net.prayforyou.backend.presenter.response.BlueTeam
import net.prayforyou.backend.presenter.response.ClanListResponse
import net.prayforyou.backend.presenter.response.ClanRankingResponse
import net.prayforyou.backend.presenter.response.ClanResponse
import net.prayforyou.backend.presenter.response.DetailUser
import net.prayforyou.backend.presenter.response.MatchDetail
import net.prayforyou.backend.presenter.response.MatchResponse
import net.prayforyou.backend.presenter.response.RedTeam
import net.prayforyou.backend.presenter.response.User
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/clan")
class ClanController(
    private val clanService: ClanService,
    private val matchService: MatchService
) {

    @GetMapping("/list")
    fun getClanList(): MutableList<ClanListResponse> {
        val clanListResponse: MutableList<ClanListResponse> = mutableListOf()
        for (clan in clanService.findAll()) {
            clanListResponse.add(ClanListResponse(clan.clanId, clan.clanName))
        }

        return clanListResponse
    }

    @GetMapping("/home")
    fun getClanHome(): CommonResponse<List<ClanRankingResponse>> {
        return CommonResponse.convert(clanService.getClanOrderByCreatedAt().map { ClanRankingResponse.from(it) }
            .toList())
    }

    @GetMapping("/{clanId}")
    fun getClanInfo(@PathVariable clanId: String): ClanResponse {
        val from = ClanResponse.from(clanService.getClanById(clanId)!!)
        from.ranking =
            clanService.findAll().sortedBy { it.score }.reversed().indexOf(clanService.getClanById(clanId)) + 1
        return from
    }

    @GetMapping("/ranking")
    fun getClanInfoOrderByRanking(@RequestParam("levelName") levelName: ClanLevel): CommonResponse<List<ClanRankingResponse>> {
        return CommonResponse.convert(
            clanService.getClanOrderByScore(levelName).map { ClanRankingResponse.from(it) }.toList()
        )
    }

    @GetMapping("/match/detail")
    fun getMatchDetail(@RequestParam("matchId") matchId: Long): CommonResponse<MatchDetail> {
        val matchDetail = matchService.getMatchDetail(matchId)
        val redUsers = matchService.getMatchUsers(matchDetail.matchId, matchDetail.redClan.id!!.toString())
        val blueUsers = matchService.getMatchUsers(matchDetail.matchId, matchDetail.blueClan.id!!.toString())
        val matchDetailResponse: MatchDetail = MatchDetail(
            matchDetail.matchStartTime,
            matchDetail.isRedTeamWin,
            redUsers.map {
                DetailUser(
                    it.user.userNexonId.toLong(),
                    it.user.nickname!!,
                    it.user.score!!.toInt(),
                    it.killCount,
                    it.deathCount,
                    it.isSniper
                )
            }.toList(),
            blueUsers.map {
                DetailUser(
                    it.user.userNexonId.toLong(),
                    it.user.nickname!!,
                    it.user.score!!.toInt(),
                    it.killCount,
                    it.deathCount,
                    it.isSniper
                )
            }.toList()
        )

        return CommonResponse.convert(matchDetailResponse)
    }

    @GetMapping("/match")
    fun getUserMatch(
        @RequestParam("clanId") clanId: String,
        @PageableDefault(value = 7) pageable: Pageable
    ): PageResponse<MutableList<MatchResponse>> {
        val matchResponse: MutableList<MatchResponse> = mutableListOf()
        val matchList = matchService.getMatchDataByClanId(clanId, pageable)
        for (match in matchList) {
            val redUsers = matchService.getMatchUsers(match.matchId, match.redClan.id!!.toString())
            val blueUsers = matchService.getMatchUsers(match.matchId, match.blueClan.id!!.toString())

            var isWin = false
            if (match.redClan.clanId == clanId) {
                isWin = match.isRedTeamWin
            } else if (match.blueClan.clanId == clanId) {
                isWin = !match.isRedTeamWin
            }

            matchResponse.add(
                MatchResponse(
                    gameProgressTime = match.totalMatchTime,
                    isWin = isWin,
                    lastGameDay = DateUtil.calculateTime(DateUtil.toDate(match.matchStartTime))!!,
                    addScore = match.plusScore,
                    matchId = match.matchId.toString(),
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
                    ),
                    isDraw = match.isDraw
                )
            )
        }

        return PageResponse.convert(matchResponse, matchList.isLast, matchList.totalPages)
    }
}
