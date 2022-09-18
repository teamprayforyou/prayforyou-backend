package net.prayforyou.backend.domain.battle

import net.prayforyou.backend.domain.battle.enums.BattleMapType
import net.prayforyou.backend.domain.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "battle_stats")
class BattleStats(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(name = "map_type")
    @Enumerated(EnumType.STRING)
    var mapType: BattleMapType,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun from(user: User, mapType: BattleMapType): BattleStats =
            BattleStats(
                user = user,
                mapType = BattleMapType.ALL_SUPPLY
            )
    }
}
