package net.prayforyou.backend.application.battle.dto

import net.prayforyou.backend.domain.battle.BattleGun
import net.prayforyou.backend.domain.battle.BattlePlace
import net.prayforyou.backend.domain.battle.BattleRound

data class SubBattleStatsDto(
    val battlePlace: List<BattlePlace>,
    val battleRound: List<BattleRound>,
    val battleGun: List<BattleGun>
)