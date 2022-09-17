package net.prayforyou.backend.domain.battle.enums

enum class BattleGunType(
    private val title: String
) {
    RIPLE("riple"),
    SNIPER("sniper"),
    THROW("throw"),
    ASSIST("assist"),
    C4_INSTALL("c4-install"),
    C4_DISMANTLE("c4-dismantle");


    companion object {
        fun convert(type: String?): BattleGunType =
            values().firstOrNull { it.title == type } ?: throw IllegalArgumentException("존재하지 않는 무기입니다")
    }
}