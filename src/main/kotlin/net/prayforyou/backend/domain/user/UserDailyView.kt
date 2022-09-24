package net.prayforyou.backend.domain.user

import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user_daily_view")
@EntityListeners(AuditingEntityListener::class)
class UserDailyView(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(name = "targeted_at")
    var targetedAt: LocalDate,

    @Column(name = "count")
    var count: Int,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun updateViewCount() {
        this.count += 1
    }

    companion object {
        fun of(user: User, now: LocalDate): UserDailyView =
            UserDailyView(
                user = user,
                targetedAt = now,
                count = 0
            )
    }
}