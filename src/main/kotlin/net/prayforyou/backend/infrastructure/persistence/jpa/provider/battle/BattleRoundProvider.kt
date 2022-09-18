package net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle

import net.prayforyou.backend.domain.battle.BattleRound
import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle.BattleRoundRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BattleRoundProvider(
    private val battleRoundRepository: BattleRoundRepository
) {
    fun findByStats(stats: BattleStats): List<BattleRound> =
        battleRoundRepository.findAllByBattleStats(stats)

    fun save(battleRound: BattleRound): BattleRound =
        battleRoundRepository.save(battleRound)
}