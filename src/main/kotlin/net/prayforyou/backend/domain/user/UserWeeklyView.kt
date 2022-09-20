package net.prayforyou.backend.domain.user

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

//@Entity
//@Table(name = "user_weekly_view")
class UserWeeklyView(
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

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
)