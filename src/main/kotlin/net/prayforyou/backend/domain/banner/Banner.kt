package net.prayforyou.backend.domain.banner

import net.prayforyou.backend.domain.banner.enums.BannerType
import net.prayforyou.backend.domain.user.enums.UserType
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "banner")
@EntityListeners(AuditingEntityListener::class)
class Banner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    var type: BannerType,

    @Column(name = "siteUrl")
    var siteUrl: String,

    @Column(name = "image_url")
    var imageUrl: String,

    @Column(name = "started_at")
    var startedAt: LocalDateTime,

    @Column(name = "ended_at")
    var endedAt: LocalDateTime,

    @Column(name = "is_deleted")
    var isDeleted: Boolean,

    @Column(name = "created_at")
    var createdAt: LocalDateTime,

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime
) {
    fun isAvailable(now: LocalDateTime): Boolean =
        now in (this.startedAt..this.endedAt)

    fun isTypeA(): Boolean = this.type == BannerType.TYPE_A

    fun isTypeB(): Boolean = this.type == BannerType.TYPE_B

    fun isTypeC(): Boolean = this.type == BannerType.TYPE_C
}