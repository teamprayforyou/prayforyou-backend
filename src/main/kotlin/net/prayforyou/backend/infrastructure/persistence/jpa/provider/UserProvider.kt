package net.prayforyou.backend.infrastructure.persistence.jpa.provider

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.exception.NotFoundDataException
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
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

    fun findAllByPageable(pageable: Pageable): Page<User> =
        userRepository.findAll(pageable)

    fun findByUserId(userId: Int): User =
        userRepository.findByIdOrNull(userId.toLong()) ?: throw NotFoundDataException(message = "유저를 찾을 수 없습니다.")
}
