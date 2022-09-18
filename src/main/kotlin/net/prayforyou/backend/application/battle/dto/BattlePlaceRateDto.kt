package net.prayforyou.backend.application.battle.dto

import net.prayforyou.backend.domain.battle.BattlePlace
import net.prayforyou.backend.domain.battle.enums.BattlePlaceType

data class BattlePlaceRateDto(
    val place: BattlePlaceType,
    val kill: Int,
    val death: Int,
    val rate: Double
) {
    companion object {
        fun from(battlePlace: BattlePlace, rate: Double): BattlePlaceRateDto =
            BattlePlaceRateDto(
                place = battlePlace.battlePosition?.battlePlaceType!!,
                kill = battlePlace.kill,
                death = battlePlace.death,
                rate = rate,
            )
    }
}