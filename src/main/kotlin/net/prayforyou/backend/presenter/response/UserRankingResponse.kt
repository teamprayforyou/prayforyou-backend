package net.prayforyou.backend.presenter.response

import net.prayforyou.backend.domain.user.User

data class UserRankingResponse(
    val userNexonId: Int,
    val name: String,
    val clanMarkUrl: String? = null,
    val winCount: Int,
    val loseCount: Int,
    val kill: Int,
    val death: Int,
    val winLosePercent: Double,
    val killDeath: Double,
    val killPerGame: Double,
    val score: Long
) {
    companion object {
        fun from(user: User): UserRankingResponse {
            return UserRankingResponse(
                userNexonId = user.userNexonId,
                name = user.nickname!!,
                clanMarkUrl = user.clanId?.clanMarkUrl
                    ?: "https://mark-clan.s3.ap-northeast-2.amazonaws.com/Rotipple+(2).png",
                winCount = user.winCount!!,
                loseCount = user.gameCount!! - user.winCount!!,
                winLosePercent = user.winLoosePercent!!,
                killDeath = user.killDeath!!,
                killPerGame = user.killPerGame!!,
                score = user.score!!,
                kill = user.killCount ?: 0,
                death = user.deathCount ?: 0
            )
        }
    }
}
