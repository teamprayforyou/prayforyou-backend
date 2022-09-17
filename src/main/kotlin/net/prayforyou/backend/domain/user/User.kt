package net.prayforyou.backend.domain.user

import net.prayforyou.backend.domain.user.enums.UserType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "nickname")
    var nickname: String,

    @Column(name = "user_nexon_id")
    var userNexonId: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    var userType: UserType,

    ) {
    fun updateNickname(nickname: String) {
        nickname.let { this.nickname = nickname }
    }
}
