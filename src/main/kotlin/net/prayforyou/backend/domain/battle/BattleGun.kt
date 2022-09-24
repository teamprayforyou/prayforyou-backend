package net.prayforyou.backend.domain.battle

import net.prayforyou.backend.domain.battle.enums.BattleGunType
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.BattleLog
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "battle_gun")
@EntityListeners(AuditingEntityListener::class)
class BattleGun(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_stats_id")
    var battleStats: BattleStats,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    var type: BattleGunType,

    @Column(name = "use_count")
    var useCount: Int,

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun updateUseCount(): BattleGun {
        this.useCount += 1
        return this
    }

    fun isSameGun(gunType: BattleGunType): Boolean =
        this.type == gunType

    companion object {
        fun from(type: BattleGunType, stats: BattleStats, useCount: Int = 0): BattleGun =
            BattleGun(
                battleStats = stats,
                type = type,
                useCount = useCount
            )
    }
}