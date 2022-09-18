package net.prayforyou.backend.application

import net.prayforyou.backend.application.dto.BattleGunUsageDto
import net.prayforyou.backend.application.dto.BattlePlaceRateDto
import net.prayforyou.backend.application.dto.BattleRoundRateDto
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.ApplicationService
import net.prayforyou.backend.global.util.MathUtil
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.UserProvider
import org.springframework.transaction.annotation.Transactional


@Transactional
@ApplicationService
class SearchApplicationService(
    private val userProvider: UserProvider,
    private val getBattleStatsService: GetBattleStatsService
) {
    fun searchByNickname(nickname: String): List<User> =
        userProvider.findContainsByNickname(nickname)

    fun searchPlaceByUserId(userId: Int): List<BattlePlaceRateDto> {
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

    fun searchRoundByUserId(userId: Int): List<BattleRoundRateDto> {
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

    fun searchGunByUserId(userId: Int): List<BattleGunUsageDto> {
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