package net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle

import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.battle.enums.BattleMapType
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle.BattleStatsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BattleStatsProvider(
    private val battleStatsRepository: BattleStatsRepository
) {
    fun findByUserAndMapType(user: User, mapType: BattleMapType): BattleStats =
        battleStatsRepository.findByUserAndMapType(user, mapType)

    fun findNullableByUserAndMapType(user: User, mapType: BattleMapType): BattleStats? =
        battleStatsRepository.findNullableByUserAndMapType(user, mapType)

    fun save(stats: BattleStats): BattleStats =
        battleStatsRepository.save(stats)
}