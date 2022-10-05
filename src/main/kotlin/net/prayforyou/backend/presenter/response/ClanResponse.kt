package net.prayforyou.backend.presenter.response

import net.prayforyou.backend.domain.clan.Clan

data class ClanResponse(
    val name: String,
    val clanLevel: String,
    var ranking: Int? = null,
    val ladderPoint: Long,
    val winLosePercent: Double,
    val winCount: Int,
    val loseCount: Int
) {
    companion object {
        fun from(clan: Clan): ClanResponse {
            return ClanResponse(
                name = clan.clanName,
                clanLevel = clan.clanLevel,
                ladderPoint = clan.score!!,
                winLosePercent = clan.winLosePercent,
                winCount = clan.winCount,
                loseCount = clan.loseCount
            )
        }
    }
}
