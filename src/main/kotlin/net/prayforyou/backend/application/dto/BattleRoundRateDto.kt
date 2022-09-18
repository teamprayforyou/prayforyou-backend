package net.prayforyou.backend.application.dto

import net.prayforyou.backend.domain.battle.BattleRound

data class BattleRoundRateDto(
    val round: Int,
    val kill: Int,
    val death: Int,
    val rate: Double
) {
    companion object {
        fun from(battleRound: BattleRound, rate: Double): BattleRoundRateDto =
            BattleRoundRateDto(
                round = battleRound.round,
                kill = battleRound.kill,
                death = battleRound.death,
                rate = rate
            )
    }
}