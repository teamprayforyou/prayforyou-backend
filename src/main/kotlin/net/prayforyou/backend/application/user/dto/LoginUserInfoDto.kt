package net.prayforyou.backend.application.user.dto

import net.prayforyou.backend.domain.user.User

data class LoginUserInfoDto(
    val id: Long,
    val email: String,
    val nickname: String,
    val score: Long,
    val killDeath: Double,
    val killCount: Int,
    val deathCount: Int,
    val gameCount: Int,
    val primaryUseGun: String?,
    val clanId: Long?,
    val clanNickname: String?,
    val clanMarkUrl: String?,
    val token: String
) {
    companion object {
        fun from(user: User, token: String): LoginUserInfoDto =
            LoginUserInfoDto(
                id = user.id!!,
                email = user.email!!,
                nickname = user.nickname!!,
                score = user.score ?: 0,
                killCount = user.killCount ?: 0,
                killDeath = user.killDeath ?: 0.0,
                deathCount = user.deathCount ?: 0,
                gameCount = user.gameCount ?: 0,
                primaryUseGun = user.primaryUseGun,
                clanId = user.clanId?.id,
                clanNickname = user.clanId?.clanNickname,
                clanMarkUrl = user.clanId?.clanMarkUrl,
                token = token
            )
    }
}