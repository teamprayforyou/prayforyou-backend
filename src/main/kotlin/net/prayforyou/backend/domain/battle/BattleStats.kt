package net.prayforyou.backend.domain.battle

import net.prayforyou.backend.domain.battle.enums.BattleMapType
import net.prayforyou.backend.domain.user.User
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "battle_stats")
@EntityListeners(AuditingEntityListener::class)
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

    @Column(name = "game_count")
    var gameCount: Int = 0,

    @Column(name = "kill_count")
    var kill: Int = 0,

    @Column(name = "death_count")
    var death: Int = 0,

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {

    fun increaseKill() {
        this.kill += 1
    }

    fun increaseDeath() {
        this.death += 1
    }

    fun increaseGameCount() {
        this.gameCount += 1
    }

    companion object {
        fun from(user: User, mapType: BattleMapType, round: Int): BattleStats =
            BattleStats(
                user = user,
                mapType = mapType,
                gameCount = round
            )
    }
}
