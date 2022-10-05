package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.clan.ClanService
import net.prayforyou.backend.application.match.MatchService
import net.prayforyou.backend.domain.clan.Clan
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.response.BlueTeam
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

    @GetMapping("/{clanId}")
    fun getClanInfo(@PathVariable clanId: Long): ClanResponse {
        val from = ClanResponse.from(clanService.getClanById(clanId)!!)
        from.ranking = clanService.findAll().sortedBy { it.score }.indexOf(clanService.getClanById(clanId)) + 1
        return from
    }

    @GetMapping("/ranking")
    fun getClanInfoOrderByRanking(): CommonResponse<List<Clan>> {
        return CommonResponse.convert(clanService.getClanOrderByScore())
    }

    @GetMapping("/match/detail")
    fun getMatchDetail(@RequestParam("matchId") matchId: Long): CommonResponse<MatchDetail> {
        val matchDetail = matchService.getMatchDetail(matchId)
        val redUsers = matchService.getMatchUsers(matchDetail.matchId, matchDetail.redClan.id!!)
        val blueUsers = matchService.getMatchUsers(matchDetail.matchId, matchDetail.blueClan.id!!)
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
        @RequestParam("clanId") clanId: Long,
        @PageableDefault(value = 7) pageable: Pageable
    ): CommonResponse<MutableList<MatchResponse>> {
        val matchResponse: MutableList<MatchResponse> = mutableListOf()
        val matchList = matchService.getMatchDataByClanId(clanId, pageable)
        for (match in matchList) {
            val redUsers = matchService.getMatchUsers(match.matchId, match.redClan.id!!)
            val blueUsers = matchService.getMatchUsers(match.matchId, match.blueClan.id!!)
            matchResponse.add(
                MatchResponse(
                    gameProgressTime = match.totalMatchTime,
                    isWin = match.isRedTeamWin,
                    lastGameDay = match.matchTimeKorean,
                    addScore = match.plusScore,
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
