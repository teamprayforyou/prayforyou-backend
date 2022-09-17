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

    fun findNullableByUserNexonId(userId: Int): User? =
        userRepository.findByUserNexonId(userId)

    fun findContainsByNickname(nickname: String): List<User> =
        userRepository.findByNicknameContains(nickname)

    fun save(user: User): User = userRepository.save(user)
}
