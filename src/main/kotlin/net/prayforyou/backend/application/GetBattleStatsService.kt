package net.prayforyou.backend.application

import net.prayforyou.backend.domain.battle.BattleGun
import net.prayforyou.backend.domain.battle.BattlePlace
import net.prayforyou.backend.domain.battle.BattleRound
import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.*
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
        val stats = battleStatsProvider.findByUser(user)
        return battlePlaceProvider.findByStats(stats)
    }

    fun getRoundByUser(user: User): List<BattleRound> {
        val stats = battleStatsProvider.findByUser(user)
        return battleRoundProvider.findByStats(stats)
    }

    fun getGunByUser(user: User): List<BattleGun> {
        val stats = battleStatsProvider.findByUser(user)
        return battleGunProvider.findByStats(stats)
    }
}