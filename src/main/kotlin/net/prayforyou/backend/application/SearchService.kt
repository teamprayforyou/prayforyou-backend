package net.prayforyou.backend.application

import net.prayforyou.backend.application.dto.BattleGunUsageDto
import net.prayforyou.backend.application.dto.BattlePlaceRateDto
import net.prayforyou.backend.application.dto.BattleRoundRateDto
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.util.MathUtil
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.BattleRoundProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.UserProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SearchService(
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

        return battlePlaceList.sortedBy { it.rate }
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

        return battleRoundList.sortedBy { it.rate }
    }

    fun searchGunByUserId(userId: Int): List<BattleGunUsageDto> {
        val user = userProvider.findByUserId(userId)
        val battleRound = getBattleStatsService.getGunByUser(user)

        val battleGunList = mutableListOf<BattleGunUsageDto>()
        battleRound.forEach {
            battleGunList.add(
                BattleGunUsageDto(it.type, it.useCount)
            )
        }

        return battleGunList.sortedBy { it.useCount }
    }
}