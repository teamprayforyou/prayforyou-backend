package net.prayforyou.backend.domain.battle.enums

import net.prayforyou.backend.global.common.exception.NotFoundDataException

enum class BattleGunType(
    private val title: String,
    private val description: String
) {
    RIPLE("riple", "라플"),
    SNIPER("sniper", "스나"),
    THROW("throw", "투척무기"),
    ASSIST("assist", "어시스트"),
    C4_INSTALL("c4-install", ""),
    C4_DISMANTLE("c4-dismantle", ""),
    NOT_WEAPON("", "");


    companion object {
        fun convert(type: String?): BattleGunType =
            values().firstOrNull { it.title == type } ?: NOT_WEAPON
    }
}