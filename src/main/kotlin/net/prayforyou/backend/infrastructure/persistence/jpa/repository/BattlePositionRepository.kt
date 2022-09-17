package net.prayforyou.backend.infrastructure.persistence.jpa.repository

import net.prayforyou.backend.domain.battle.BattlePosition
import org.springframework.data.jpa.repository.JpaRepository

interface BattlePositionRepository : JpaRepository<BattlePosition, Long> {
}
