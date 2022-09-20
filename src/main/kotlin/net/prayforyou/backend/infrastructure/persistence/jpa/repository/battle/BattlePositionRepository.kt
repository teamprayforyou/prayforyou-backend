package net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle

import net.prayforyou.backend.domain.battle.BattlePosition
import net.prayforyou.backend.domain.battle.enums.BattlePlaceType
import org.springframework.data.jpa.repository.JpaRepository

interface BattlePositionRepository : JpaRepository<BattlePosition, Long> {
    fun findByBattlePlaceType(type: BattlePlaceType): BattlePosition?
}
