package net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle

import net.prayforyou.backend.domain.battle.BattleRound
import net.prayforyou.backend.domain.battle.BattleStats
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BattleRoundRepository : JpaRepository<BattleRound, Long> {
    fun findAllByBattleStats(stats: BattleStats): List<BattleRound>
}