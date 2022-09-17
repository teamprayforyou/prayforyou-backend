package net.prayforyou.backend.domain.battle

import net.prayforyou.backend.domain.battle.enums.BattleGunType
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.BattleLog
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "battle_gun")
class BattleGun(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_stats_id")
    var battleStats: BattleStats,

    @Column(name = "type")
    var type: BattleGunType,

    @Column(name = "use_count")
    var useCount: Int,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun updateUseCount(): BattleGun {
        this.useCount += 1
        return this
    }

    companion object {
        fun from(type: BattleGunType, stats: BattleStats, useCount: Int = 0): BattleGun =
            BattleGun(
                battleStats = stats,
                type = type,
                useCount = useCount
            )
    }
}