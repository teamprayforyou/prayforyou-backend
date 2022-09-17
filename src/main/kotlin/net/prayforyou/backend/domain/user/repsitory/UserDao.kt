package net.prayforyou.backend.domain.user.repsitory

import net.prayforyou.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserDao: JpaRepository<User, Long> {
    fun findByUserNexonId(userId: Int): User?
}
