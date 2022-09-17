package net.prayforyou.backend.domain.battle.enums

enum class BattlePlaceType(
    val description: String
) {
    SITE_A("기억자");

    companion object {
        fun convert(killX: Double?, killY: Double?): BattlePlaceType {
            // TODO 범위 계산

            return SITE_A
        }
    }
}
