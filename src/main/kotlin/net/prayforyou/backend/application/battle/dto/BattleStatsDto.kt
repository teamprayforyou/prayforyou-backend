package net.prayforyou.backend.application.battle.dto

import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.user.User

data class BattleStatsDto(
    val kill: Int,
    val death: Int,
    val gameCount: Int,
    val rate: Double,
    val updatedAt: String
) {
    companion object {
        fun from(stats: BattleStats, rate: Double): BattleStatsDto =
            BattleStatsDto(
                kill = stats.kill,
                death = stats.death,
                gameCount = stats.gameCount,
                rate = rate,
                updatedAt = stats.updatedAt.toString()
            )
    }
}