package net.prayforyou.backend.presenter.response

import net.prayforyou.backend.domain.user.User

data class UserResponse(
    val name: String,
    val weapon: String,
    val killDeath: Double,
    val killPerGame: Double,
    val ladderPoint: Long,
    val winLosePercent: Double,
    var ranking: Int? = null,
    val clanName: String,
    val clanMarkUrl: String
) {
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                name = user.nickname!!,
                weapon = user.primaryUseGun!!,
                killDeath = user.killDeath!!,
                killPerGame = user.killPerGame!!,
                ladderPoint = user.score!!,
                winLosePercent = user.winLoosePercent!!,
                clanName = user.clanId?.clanNickname
                    ?: "무소속",
                clanMarkUrl = user.clanId?.clanMarkUrl
                    ?: "https://mark-clan.s3.ap-northeast-2.amazonaws.com/Rotipple+(2).png"
            )
        }
    }
}
