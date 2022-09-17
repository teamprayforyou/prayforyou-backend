package net.prayforyou.backend.domain.battlelog.repository

import net.prayforyou.backend.domain.battlelog.entity.BattleLog
import org.springframework.stereotype.Component

@Component
class BattleRepository(
    private val battleLogDao: BattleLogDao
) {
    fun save(battleLog: BattleLog) {
        battleLogDao.save(battleLog)
    }
}
