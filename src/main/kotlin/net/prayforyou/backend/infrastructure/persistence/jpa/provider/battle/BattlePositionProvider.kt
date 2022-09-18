package net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle

import net.prayforyou.backend.domain.battle.BattlePosition
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle.BattlePositionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BattlePositionProvider(
    private val battlePositionRepository: BattlePositionRepository
) {
    fun findAll(): List<BattlePosition> = battlePositionRepository.findAll()
}
