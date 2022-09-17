package net.prayforyou.backend.application.dto

import net.prayforyou.backend.domain.battle.BattlePlace

data class BattlePlaceRateDto(
    val kill: Int,
    val death: Int,
    val rate: Double
) {
    companion object {
        fun from(battlePlace: BattlePlace, rate: Double): BattlePlaceRateDto =
            BattlePlaceRateDto(
                kill = battlePlace.kill,
                death = battlePlace.death,
                rate = rate
            )
    }
}