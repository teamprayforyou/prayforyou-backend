package net.prayforyou.backend.application.battle

import net.prayforyou.backend.application.battle.dto.BattleGunUsageDto
import net.prayforyou.backend.application.battle.dto.BattlePlaceRateDto
import net.prayforyou.backend.application.battle.dto.BattleRoundRateDto
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.annotation.ApplicationService
import net.prayforyou.backend.global.common.exception.ValidationException
import net.prayforyou.backend.global.util.MathUtil
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserProvider
import org.springframework.transaction.annotation.Transactional


@Transactional
@ApplicationService
class SearchApplicationService(
    private val userProvider: UserProvider,
    private val getBattleStatsService: GetBattleStatsService
) {
    fun searchPlaceByUserId(userId: Long): List<BattlePlaceRateDto> {
        val user = userProvider.findByUserId(userId)
        val place = getBattleStatsService.getPlaceByUser(user)
        val battlePlaceList = mutableListOf<BattlePlaceRateDto>()

        place.forEach {
            battlePlaceList.add(
                BattlePlaceRateDto.from(it, MathUtil.getRate(it.kill, it.death))
            )
        }

        return battlePlaceList.sortedByDescending { it.rate }
    }

    fun searchRoundByUserId(userId: Long): List<BattleRoundRateDto> {
        val user = userProvider.findByUserId(userId)
        val battleRound = getBattleStatsService.getRoundByUser(user)

        val battleRoundList = mutableListOf<BattleRoundRateDto>()
        battleRound.forEach {
            battleRoundList.add(
                BattleRoundRateDto.from(it, MathUtil.getRate(it.kill, it.death))
            )
        }

        return battleRoundList.sortedByDescending { it.rate }
    }

    fun searchGunByUserId(userId: Long): List<BattleGunUsageDto> {
        val user = userProvider.findByUserId(userId)
        val battleGun = getBattleStatsService.getGunByUser(user)

        val battleGunList = mutableListOf<BattleGunUsageDto>()
        battleGun.forEach {
            battleGunList.add(
                BattleGunUsageDto(it.type, it.useCount)
            )
        }

        return battleGunList.sortedByDescending { it.useCount }
    }
}