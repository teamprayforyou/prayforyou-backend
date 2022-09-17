package net.prayforyou.backend.domain.battle

import net.prayforyou.backend.domain.battle.enums.BattleGunType
import net.prayforyou.backend.domain.battle.enums.BattleMapType
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

    @Column(name = "user_count")
    var useCount: Int,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)