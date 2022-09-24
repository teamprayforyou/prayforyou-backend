package net.prayforyou.backend.domain.battle

import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "battle_round")
@EntityListeners(AuditingEntityListener::class)
class BattleRound(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_stats_id")
    var battleStats: BattleStats,

    @Column(name = "round")
    var round: Int,

    @Column(name = "kill_count")
    var kill: Int,

    @Column(name = "death_count")
    var death: Int,

    @LastModifiedDate
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

    fun isSameRound(round: Int): Boolean = this.round == round

    companion object {
        fun from(round: Int, stats: BattleStats, kill: Int = 0, death: Int = 0): BattleRound =
            BattleRound(
                battleStats = stats,
                round = round,
                kill = kill,
                death = death
            )
    }
}
