package net.prayforyou.backend.application.battle.dto

import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.user.User

data class BattleStatsDto(
    val userId: Long,
    val nickname: String,
    val userNexonId: Int,
    val userType: String,
    val kill: Int,
    val death: Int,
    val round: Int,
    val rate: Double
) {
    companion object {
        fun from(user: User, stats: BattleStats, rate: Double): BattleStatsDto =
            BattleStatsDto(
                userId = user.id!!,
                nickname = user.nickname!!,
                userNexonId = user.userNexonId,
                userType = user.userType.description,
                kill = stats.kill,
                death = stats.death,
                round = stats.round,
                rate = rate
            )
    }
}