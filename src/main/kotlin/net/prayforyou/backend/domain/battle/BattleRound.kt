package net.prayforyou.backend.domain.battle

import net.prayforyou.backend.infrastructure.crawler.webclient.dto.BattleLog
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "battle_round")
class BattleRound(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_stats_id")
    var battleStats: BattleStats,

    @Column(name = "round")
    var round: Int,

    @Column(name = "kill")
    var kill: Int,

    @Column(name = "death")
    var death: Int,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun updateKill(): BattleRound? {
        this.kill += 1
        return this
    }

    fun updateDeath(): BattleRound? {
        this.death += 1
        return this
    }

    companion object {
        fun from(battleLog: BattleLog, stats: BattleStats, kill: Int = 0, death: Int = 0): BattleRound =
            BattleRound(
                battleStats = stats,
                round = battleLog.round?.toInt()!!,
                kill = kill,
                death = death
            )
    }
}