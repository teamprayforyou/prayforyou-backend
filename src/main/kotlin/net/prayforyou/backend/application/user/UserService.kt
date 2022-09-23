package net.prayforyou.backend.application.user

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.annotation.ApplicationService
import net.prayforyou.backend.global.common.exception.ValidationException
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserProvider
import org.springframework.transaction.annotation.Transactional

@Transactional
@ApplicationService
class UserService(
    private val userProvider: UserProvider
) {

    fun searchByNickname(nickname: String): List<User> {
        if (nickname.isBlank()) {
            throw ValidationException("닉네임을 입력해주세요")
        }

        return userProvider.findContainsByNickname(nickname)
    }

    fun getByUserId(userId: Long): User =
        userProvider.findByUserId(userId)
}