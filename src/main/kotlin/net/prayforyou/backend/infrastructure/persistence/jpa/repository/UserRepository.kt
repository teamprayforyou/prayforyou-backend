package net.prayforyou.backend.infrastructure.persistence.jpa.repository

import net.prayforyou.backend.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByUserNexonId(userId: Int): User?
    fun findByNicknameContains(nickname: String): List<User>
}
