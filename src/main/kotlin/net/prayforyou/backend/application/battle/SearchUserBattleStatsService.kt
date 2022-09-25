package net.prayforyou.backend.application.battle

import net.prayforyou.backend.application.battle.dto.*
import net.prayforyou.backend.domain.battle.enums.BattleMapType
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.annotation.ApplicationService
import net.prayforyou.backend.global.common.exception.NotFoundDataException
import net.prayforyou.backend.global.util.MathUtil
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle.BattleStatsRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional


@Transactional
@ApplicationService
class SearchUserBattleStatsService(
    private val userRepository: UserRepository,
    private val getBattleStatsService: GetBattleStatsService,
    private val battleStatsRepository: BattleStatsRepository
) {
    fun getBattleStatsByUserId(userId: Long): SearchUserBattleStatsDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundDataException("유저 정보를 찾을 수 없습니다.")
        val stats =
            battleStatsRepository.findByUserAndMapType(user, BattleMapType.ALL_SUPPLY)
                ?: throw NotFoundDataException("전적정보가 존재하지 않습니다.")

        return SearchUserBattleStatsDto.of(
            addPlaceByDescending(user),
            addRoundByDescending(user),
            addGunByDescending(user),
            user, BattleStatsDto.from(stats, MathUtil.getRate(stats.kill, stats.death))
        )
    }

    private fun addPlaceByDescending(user: User): List<BattlePlaceRateDto> {
        val place = getBattleStatsService.getPlaceByUser(user)
        val battlePlaceList = mutableListOf<BattlePlaceRateDto>()

        place.forEach {
            battlePlaceList.add(
                BattlePlaceRateDto.from(it, MathUtil.getRate(it.kill, it.death))
            )
        }

        return battlePlaceList.sortedByDescending { it.rate }
    }

    private fun addRoundByDescending(user: User): List<BattleRoundRateDto> {
        val battleRound = getBattleStatsService.getRoundByUser(user)

        val battleRoundList = mutableListOf<BattleRoundRateDto>()
        battleRound.forEach {
            battleRoundList.add(
                BattleRoundRateDto.from(it, MathUtil.getRate(it.kill, it.death))
            )
        }

        return battleRoundList.sortedByDescending { it.rate }
    }

    private fun addGunByDescending(user: User): List<BattleGunUsageDto> {
        val battleGun = getBattleStatsService.getGunByUser(user)

        val battleGunList = mutableListOf<BattleGunUsageDto>()
        battleGun.forEach {
            battleGunList.add(
                BattleGunUsageDto(it.type.description, it.useCount)
            )
        }

        return battleGunList.sortedByDescending { it.useCount }
    }
}