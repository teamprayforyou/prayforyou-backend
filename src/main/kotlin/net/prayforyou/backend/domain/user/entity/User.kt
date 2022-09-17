package net.prayforyou.backend.domain.user.entity

import net.prayforyou.backend.domain.model.UserType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    private var nickname: String,

    private var userNexonId: Int,

    @Enumerated(EnumType.STRING)
    private var userType: UserType,

    ) {
    fun updateNickname(nickname: String) {
        nickname.let { this.nickname = nickname }
    }
}
