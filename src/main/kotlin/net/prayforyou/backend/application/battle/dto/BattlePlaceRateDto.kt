package net.prayforyou.backend.application.battle.dto

import net.prayforyou.backend.domain.battle.BattlePlace

data class BattlePlaceRateDto(
    val place: String,
    val kill: Int,
    val death: Int,
    val rate: Double
) {
    companion object {
        fun from(battlePlace: BattlePlace, rate: Double): BattlePlaceRateDto =
            BattlePlaceRateDto(
                place = battlePlace.battlePosition?.battlePlaceType!!.description,
                kill = battlePlace.kill,
                death = battlePlace.death,
                rate = rate,
            )
    }
}
