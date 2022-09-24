package net.prayforyou.backend.domain.battle

import net.prayforyou.backend.domain.battle.enums.BattlePlaceType
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "battle_place")
@EntityListeners(AuditingEntityListener::class)
class BattlePlace(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_stats_id")
    var battleStats: BattleStats,

    @OneToOne(fetch = FetchType.LAZY)
    var battlePosition: BattlePosition? = null,

    @Column(name = "kill_count")
    var kill: Int = 0,

    @Column(name = "death_count")
    var death: Int = 0,

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {

    fun updateKill(): BattlePlace {
        this.kill += 1
        return this
    }

    fun updateDeath(): BattlePlace {
        this.death += 1
        return this
    }

    fun isSamePosition(placeType: BattlePlaceType): Boolean =
        this.battlePosition?.battlePlaceType == placeType

    companion object {
        fun from(stats: BattleStats, kill: Int = 0, death: Int = 0, battlePosition: BattlePosition?): BattlePlace =
            BattlePlace(
                battleStats = stats,
                kill = kill,
                death = death,
                battlePosition = battlePosition
            )
    }
}
