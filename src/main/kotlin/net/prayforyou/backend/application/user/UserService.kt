package net.prayforyou.backend.application.user

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.annotation.ApplicationService
import net.prayforyou.backend.global.common.exception.NotFoundDataException
import net.prayforyou.backend.global.common.exception.ValidationException
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@Transactional
@ApplicationService
class UserService(
    private val userRepository: UserRepository
) {

    fun searchByNickname(nickname: String): List<User> {
        if (nickname.isBlank()) {
            throw ValidationException("닉네임을 입력해주세요")
        }

        return userRepository.findByNicknameContains(nickname)
    }
}