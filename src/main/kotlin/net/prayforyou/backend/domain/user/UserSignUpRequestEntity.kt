package net.prayforyou.backend.domain.user

import net.prayforyou.backend.domain.user.enums.UserSignUpStatus
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user_sign_up_request")
@EntityListeners(AuditingEntityListener::class)
class UserSignUpRequestEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "clan_unique_number")
    val clanId: String,

    @Column(name = "nickname")
    val nickname: String,

    @Column(name = "type")
    val type: UserSignUpStatus = UserSignUpStatus.WAIT,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun from(clanId: String, email: String, nickname: String, password: String): UserSignUpRequestEntity =
            UserSignUpRequestEntity(
                email = email,
                nickname = nickname,
                password = password,
                clanId = clanId
            )
    }
}