package net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle

import net.prayforyou.backend.domain.battle.BattleGun
import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.battle.enums.BattleGunType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BattleGunRepository : JpaRepository<BattleGun, Long> {
    fun findAllByBattleStats(stats: BattleStats): List<BattleGun>
}