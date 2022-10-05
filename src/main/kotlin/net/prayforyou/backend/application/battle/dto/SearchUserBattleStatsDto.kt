package net.prayforyou.backend.application.battle.dto

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.enums.UserType

data class SearchUserBattleStatsDto(
    val userId: Long,
    val nickname: String?,
    val battleGun: List<BattleGunUsageDto>,
    val battlePlace: List<BattlePlaceRateDto>,
) {
    companion object {
        fun of(
            battlePlace: List<BattlePlaceRateDto>,
            battleGun: List<BattleGunUsageDto>,
            user: User,
        ): SearchUserBattleStatsDto =
            SearchUserBattleStatsDto(
                userId = user.id!!,
                nickname = user.nickname,
                battleGun = battleGun,
                battlePlace = battlePlace,
            )
    }
}
