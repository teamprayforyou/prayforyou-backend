package net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle

import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BattleStatsRepository : JpaRepository<BattleStats, Long> {
    fun findByUser(user: User): BattleStats
    fun findAllByUserIdIn(userId: Collection<Long>): List<BattleStats>
}