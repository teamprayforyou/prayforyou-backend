package net.prayforyou.backend.infrastructure.persistence.jpa.provider

import net.prayforyou.backend.domain.battlelog.BattleLog
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.BattleLogRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class BattleProvider(
    private val battleLogRepository: BattleLogRepository
) {

    fun save(battleLog: BattleLog) {
        battleLogRepository.save(battleLog)
    }
}
