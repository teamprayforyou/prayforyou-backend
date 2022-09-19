package net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle

import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle.BattleStatsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BattleStatsProvider(
    private val battleStatsRepository: BattleStatsRepository
) {
    fun findByUser(user: User): BattleStats =
        battleStatsRepository.findByUser(user)

    fun save(stats: BattleStats): BattleStats =
        battleStatsRepository.save(stats)

    fun findAllByUserIdIn(userId: Collection<Long>): List<BattleStats> =
        battleStatsRepository.findAllByUserIdIn(userId)
}