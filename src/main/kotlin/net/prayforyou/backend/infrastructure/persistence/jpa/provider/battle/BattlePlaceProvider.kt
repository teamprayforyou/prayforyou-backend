package net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle

import net.prayforyou.backend.domain.battle.BattlePlace
import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle.BattlePlaceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BattlePlaceProvider(
    private val battlePlaceRepository: BattlePlaceRepository
) {
    fun findByStats(stats: BattleStats): List<BattlePlace> =
        battlePlaceRepository.findAllByBattleStats(stats)

    fun save(battlePlace: BattlePlace): BattlePlace = battlePlaceRepository.save(battlePlace)
}