package net.prayforyou.backend.infrastructure.persistence.jpa.repository

import net.prayforyou.backend.domain.battlelog.BattleLog
import org.springframework.data.jpa.repository.JpaRepository

interface BattleLogRepository: JpaRepository<BattleLog, Long> {
}
