package net.prayforyou.backend.infrastructure.persistence.jpa.repository

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.enums.UserType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUserNexonId(userId: Int): User?
    fun findByNicknameContains(nickname: String): List<User>
    fun findAllByUserType(type: UserType, pageable: Pageable): Page<User>
}
