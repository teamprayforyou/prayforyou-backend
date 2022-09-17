package net.prayforyou.backend.infrastructure.persistence.jpa.provider

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
@Transactional
class UserProvider(
    private val userRepository: UserRepository
) {

    fun findByUserNexonId(userId: Int): User? {
        return userRepository.findByUserNexonId(userId)
    }

    fun save(user: User) {
        userRepository.save(user)
    }
}
