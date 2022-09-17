package net.prayforyou.backend.domain.battle

import net.prayforyou.backend.domain.battle.enums.BattlePlaceType
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.BattleLog
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "battle_place")
class BattlePlace(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_stats_id")
    var battleStats: BattleStats,

    @Column(name = "type")
    var type: BattlePlaceType,

    @Column(name = "kill")
    var kill: Int = 0,

    @Column(name = "death")
    var death: Int = 0,

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

    companion object {
        fun from(stats: BattleStats, kill: Int = 0, death: Int = 0): BattlePlace =
            BattlePlace(
                battleStats = stats,
                type = BattlePlaceType.SITE_A, // FIXME 위치 맵핑시키는 작업 필요
                kill = kill,
                death = death
            )
    }
}