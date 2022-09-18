package net.prayforyou.backend.application

import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.battle.enums.BattleMapType
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.BattleLog
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.BattleStatsProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SaveBattleStatsService(
    private val battleStatsProvider: BattleStatsProvider,
    private val saveSubBattleStatsService: SaveSubBattleStatsService
) {

    fun save(battleLog: BattleLog, user: User, battleStatsMap: Map<Int, BattleStats>) {
        // BattleStats가 존재할 경우 사용하고, 사용하지 않을 경우 저장
        val stats = battleStatsMap[battleLog.userNexonSn!!] ?: battleStatsProvider.save(
            BattleStats.from(user, BattleMapType.ALL_SUPPLY)
        )

        // SubBattleStats를 kill, death에 따라 저장
        if (battleLog.isKill()) {
            saveSubBattleStatsService.saveByKill(battleLog, stats, user)
        } else {
            saveSubBattleStatsService.saveByDeath(battleLog, stats, user)
        }
    }
}

