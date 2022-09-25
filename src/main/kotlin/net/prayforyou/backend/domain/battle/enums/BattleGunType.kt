package net.prayforyou.backend.domain.battle.enums

enum class BattleGunType(
    val title: String,
    val description: String
) {
    RIPLE("riple", "돌격소총"),
    SNIPER("sniper", "스나"),
    THROW("throw", "투척무기"),
    SPECIAL("special", "특수총"),
    ASSIST("assist", "권총"),
    C4_INSTALL("c4-install", ""),
    C4_DISMANTLE("c4-dismantle", ""),
    NOT_WEAPON("", "");


    companion object {
        fun convert(type: String?): BattleGunType =
            values().firstOrNull { it.title == type } ?: NOT_WEAPON
    }
}