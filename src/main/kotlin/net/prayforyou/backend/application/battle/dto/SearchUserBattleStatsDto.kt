package net.prayforyou.backend.application.battle.dto

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.enums.UserType

data class SearchUserBattleStatsDto(
    val userId: Long,
    val nickname: String?,
    val userType: UserType,
    val battleGun: List<BattleGunUsageDto>,
    val battlePlace: List<BattlePlaceRateDto>,
    val battleRound: List<BattleRoundRateDto>,
    val battleStats: BattleStatsDto
) {
    companion object {
        fun of(
            battlePlace: List<BattlePlaceRateDto>,
            battleRound: List<BattleRoundRateDto>,
            battleGun: List<BattleGunUsageDto>,
            user: User,
            stats: BattleStatsDto
        ): SearchUserBattleStatsDto =
            SearchUserBattleStatsDto(
                userId = user.id!!,
                nickname = user.nickname,
                userType = user.userType,
                battleGun = battleGun,
                battlePlace = battlePlace,
                battleRound = battleRound,
                battleStats = stats
            )
    }
}