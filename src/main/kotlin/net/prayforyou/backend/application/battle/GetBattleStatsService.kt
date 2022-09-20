package net.prayforyou.backend.application.battle

import net.prayforyou.backend.application.battle.dto.SubBattleStatsDto
import net.prayforyou.backend.domain.battle.BattleGun
import net.prayforyou.backend.domain.battle.BattlePlace
import net.prayforyou.backend.domain.battle.BattleRound
import net.prayforyou.backend.domain.battle.enums.BattleMapType
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattleGunProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattlePlaceProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattleRoundProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattleStatsProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GetBattleStatsService(
    private val battleStatsProvider: BattleStatsProvider,
    private val battlePlaceProvider: BattlePlaceProvider,
    private val battleRoundProvider: BattleRoundProvider,
    private val battleGunProvider: BattleGunProvider
) {
    fun getPlaceByUser(user: User): List<BattlePlace> {
        val stats = battleStatsProvider.finByUserAndMapType(user, BattleMapType.ALL_SUPPLY)
        return battlePlaceProvider.findByStats(stats)
    }

    fun getRoundByUser(user: User): List<BattleRound> {
        val stats = battleStatsProvider.finByUserAndMapType(user, BattleMapType.ALL_SUPPLY)
        return battleRoundProvider.findByStats(stats)
    }

    fun getGunByUser(user: User): List<BattleGun> {
        val stats = battleStatsProvider.finByUserAndMapType(user, BattleMapType.ALL_SUPPLY)
        return battleGunProvider.findByStats(stats)
    }

    fun getSubStatsByUser(user: User): SubBattleStatsDto =
        SubBattleStatsDto(
            battleGun = getGunByUser(user),
            battleRound = getRoundByUser(user),
            battlePlace = getPlaceByUser(user)
        )
}