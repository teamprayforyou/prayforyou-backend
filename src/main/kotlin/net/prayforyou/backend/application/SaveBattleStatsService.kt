package net.prayforyou.backend.application

import net.prayforyou.backend.domain.battle.BattleGun
import net.prayforyou.backend.domain.battle.BattlePlace
import net.prayforyou.backend.domain.battle.BattleRound
import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.battle.enums.BattleGunType
import net.prayforyou.backend.domain.battle.enums.BattleMapType
import net.prayforyou.backend.domain.battle.enums.BattlePlaceType
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.BattleLog
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.BattleGunProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.BattlePlaceProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.BattleRoundProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.BattleStatsProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SaveBattleStatsService(
    private val battleStatsProvider: BattleStatsProvider,
    private val battleGunProvider: BattleGunProvider,
    private val battlePlaceProvider: BattlePlaceProvider,
    private val battleRoundProvider: BattleRoundProvider
) {

    fun save(battleLog: BattleLog, user: User, battleStatsMap: Map<Int, BattleStats>) {
        // 기존 기록이 없을 경우 저장
        val stats = battleStatsMap[battleLog.userNexonSn!!] ?: battleStatsProvider.save(
            BattleStats.from(user, BattleMapType.ALL_SUPPLY)
        )
        val battleGunList = battleGunProvider.findByStats(stats)
        val battleRoundList = battleRoundProvider.findByStats(stats)
        val battlePlaceList = battlePlaceProvider.findByStats(stats)

        val logGunType = BattleGunType.convert(battleLog.weapon)
        val placeType = BattlePlaceType.convert(battleLog.killX, battleLog.killY)


        // TODO 정리 필요
        if (battleLog.isKill()) {
            battleRoundList.firstOrNull { it.round == battleLog.round?.toInt() }?.updateKill()
                ?: battleRoundProvider.save(BattleRound.from(battleLog, stats, kill = 1, death = 0))

            battlePlaceList.firstOrNull { it.type == placeType }?.updateKill()
                ?: battlePlaceProvider.save(BattlePlace.from(stats = stats, kill = 1, death = 0))
        } else {
            battleRoundList.firstOrNull { it.round == battleLog.round?.toInt() }?.updateDeath()
                ?: battleRoundProvider.save(BattleRound.from(battleLog, stats, kill = 0, death = 1))

            battlePlaceList.firstOrNull { it.type == placeType }?.updateDeath()
                ?: battlePlaceProvider.save(BattlePlace.from(stats = stats, kill = 0, death = 1))
        }


        // 해당 총으로 싸운적이 없을 경우 데이터 생성
        battleGunList.firstOrNull { it.type == logGunType }?.updateUseCount()
            ?: battleGunProvider.save(BattleGun.from(type = logGunType, stats = stats, useCount = 1))
    }
}

