package net.prayforyou.backend.domain.battlelog.repository

import net.prayforyou.backend.domain.battlelog.entity.BattleLog
import org.springframework.data.jpa.repository.JpaRepository

interface BattleLogDao: JpaRepository<BattleLog, Long> {
}
