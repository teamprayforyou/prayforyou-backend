package net.prayforyou.backend.infrastructure.persistence.jpa.provider

import net.prayforyou.backend.domain.battle.BattlePosition
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.BattlePositionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BattlePositionProvider(
    private val battlePositionRepository: BattlePositionRepository
) {
    fun findAll(): MutableList<BattlePosition> = battlePositionRepository.findAll()
}
