package net.prayforyou.backend.domain.user.repsitory

import net.prayforyou.backend.domain.user.entity.User
import org.springframework.stereotype.Component

@Component
class UserRepository(
    private val userDao: UserDao
) {
    fun findByUserNexonId(userId: Int): User? {
        return userDao.findByUserNexonId(userId)
    }

    fun save(user: User) {
        userDao.save(user)
    }
}
