package net.prayforyou.backend.infrastructure.persistence.jpa.provider.user

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.enums.UserType
import net.prayforyou.backend.global.common.exception.NotFoundDataException
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
@Transactional
class UserProvider(
    private val userRepository: UserRepository
) {
    fun findContainsByNickname(nickname: String): List<User> =
        userRepository.findByNicknameContains(nickname)

    fun findByUserId(userId: Long): User =
        userRepository.findByIdOrNull(userId) ?: throw NotFoundDataException(message = "유저를 찾을 수 없습니다.")

    fun findAll(): List<User> = userRepository.findAll()

    fun findAllPaging(type: UserType, pageable: Pageable): Page<User> =
        userRepository.findAllByUserType(type, pageable)

    fun findLastUserByUserType(type: UserType, pageable: Pageable): Page<User> =
        userRepository.findAllByUserType(type, pageable)

    fun saveAllUser(users: List<User>) {
        userRepository.saveAll(users)
    }

    fun saveUser(user: User): User {
        return userRepository.saveAndFlush(user)
    }

    fun findByUserNexonId(id: Int): User? {
        return userRepository.findByUserNexonId(id)
    }
}
