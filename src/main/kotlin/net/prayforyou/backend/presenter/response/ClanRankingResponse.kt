package net.prayforyou.backend.presenter.response

import net.prayforyou.backend.domain.clan.Clan
import java.time.LocalDateTime

data class ClanRankingResponse(
    var id: Long? = null,

    var clanId: Long?,

    var clanName: String?,

    var clanNickname: String?,

    var winCount: Int? = 0,

    var loseCount: Int? = 0,

    var isDownDanger: Boolean?,

    var clanLevel: String?,

    var score: Long?,

    var winLosePercent: Double?,

    var openKakaoLink: String?,

    var clanMarkUrl: String?,

    var isDeleted: Boolean?,

    var createdAt: LocalDateTime?
) {
    companion object {
        fun from (clan: Clan): ClanRankingResponse {
            return ClanRankingResponse(
                clan.id,
                clan.clanId,
                clan.clanName,
                clan.clanNickname,
                clan.winCount,
                clan.loseCount,
                clan.isDownDanger,
                clan.clanLevel.levelName,
                clan.score,
                clan.winLosePercent,
                clan.openKakaoLink,
                clan.clanMarkUrl,
                clan.isDeleted,
                clan.createdAt
            )
        }
    }
}
