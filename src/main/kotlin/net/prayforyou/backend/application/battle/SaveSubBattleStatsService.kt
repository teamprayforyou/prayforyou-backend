package net.prayforyou.backend.application.battle

import net.prayforyou.backend.domain.battle.BattleGun
import net.prayforyou.backend.domain.battle.BattlePlace
import net.prayforyou.backend.domain.battle.BattleRound
import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.battle.enums.BattleGunType
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.BattleLog
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattleGunProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattlePlaceProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattleRoundProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle.BattleStatsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SaveSubBattleStatsService(
    private val battleGunProvider: BattleGunProvider,
    private val battlePlaceProvider: BattlePlaceProvider,
    private val battleRoundProvider: BattleRoundProvider,
    private val getBattlePositionService: GetBattlePositionService,
    private val getBattleStatsService: GetBattleStatsService
) {

    fun saveByKill(battleLog: BattleLog, stats: BattleStats, user: User) {
        val subBattleStats = getBattleStatsService.getSubStatsByUser(user)
        val battlePosition = getBattlePositionService
            .getBattlePositionByXandY(battleLog.killX!!, battleLog.killY!!)

        stats.increaseKill()

        subBattleStats.battleRound.firstOrNull { it.isSameRound(battleLog.round?.toInt()!!) }?.updateKill()
            ?: battleRoundProvider.save(BattleRound.from(battleLog.round?.toInt()!!, stats, kill = 1, death = 0))

        subBattleStats.battlePlace.firstOrNull { it.isSamePosition(battlePosition.battlePlaceType) }?.updateKill()
            ?: battlePlaceProvider.save(BattlePlace.from(stats = stats, kill = 1, death = 0, battlePosition = battlePosition))

        saveUseGun(battleLog, stats)
    }

    fun saveByDeath(battleLog: BattleLog, stats: BattleStats, user: User) {
        val subBattleStats = getBattleStatsService.getSubStatsByUser(user)
        val battlePosition = getBattlePositionService
            .getBattlePositionByXandY(battleLog.deathX!!, battleLog.deathY!!)

        stats.increaseDeath()

        subBattleStats.battleRound.firstOrNull { it.isSameRound(battleLog.round?.toInt()!!) }?.updateDeath()
            ?: battleRoundProvider.save(BattleRound.from(battleLog.round?.toInt()!!, stats, kill = 0, death = 1))

        subBattleStats.battlePlace.firstOrNull { it.isSamePosition(battlePosition.battlePlaceType) }?.updateDeath()
            ?: battlePlaceProvider.save(BattlePlace.from(stats = stats, kill = 0, death = 1, battlePosition = battlePosition))
    }

    fun saveUseGun(battleLog: BattleLog, stats: BattleStats) {
        val battleGunList = battleGunProvider.findByStats(stats)
        val gunType = BattleGunType.convert(battleLog.weapon)

        battleGunList.firstOrNull { it.isSameGun(gunType) }?.updateUseCount()
            ?: battleGunProvider.save(BattleGun.from(type = gunType, stats = stats, useCount = 1))
    }
}
