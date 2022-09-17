package net.prayforyou.backend.domain.battle

import net.prayforyou.backend.domain.battle.enums.BattleMapType
import net.prayforyou.backend.domain.battle.enums.BattlePlaceType
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
    var kill: Int,

    @Column(name = "death")
    var death: Int,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)