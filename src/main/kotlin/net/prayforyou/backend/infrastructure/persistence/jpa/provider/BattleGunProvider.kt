package net.prayforyou.backend.infrastructure.persistence.jpa.provider

import net.prayforyou.backend.domain.battle.BattleGun
import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.BattleGunRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BattleGunProvider(
    private val battleGunRepository: BattleGunRepository
) {

    fun findByStats(stats: BattleStats): List<BattleGun> =
        battleGunRepository.findAllByBattleStats(stats)

    fun save(battleGun: BattleGun): BattleGun =
        battleGunRepository.save(battleGun)
}