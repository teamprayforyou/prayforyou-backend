package net.prayforyou.backend.domain.battle.enums

import net.prayforyou.backend.global.common.exception.NotFoundDataException

enum class BattleGunType(
    val title: String,
    val description: String
) {
    RIPLE("riple", "돌격소총"),
    SNIPER("sniper", "스나"),
    THROW("throw", "투척무기"),
    SPECIAL("special", "특수총"),
    ASSIST("assist", "보조무기"),
    C4_INSTALL("c4-install", ""),
    C4_DISMANTLE("c4-dismantle", ""),
    NOT_WEAPON("", "");


    companion object {
        fun convert(type: String?): BattleGunType =
            values().firstOrNull { it.title == type } ?: NOT_WEAPON
    }
}