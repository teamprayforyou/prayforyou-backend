package net.prayforyou.backend.application.dto

import net.prayforyou.backend.domain.battle.enums.BattleGunType

data class BattleGunUsageDto(
    val type: BattleGunType,
    val useCount: Int
)