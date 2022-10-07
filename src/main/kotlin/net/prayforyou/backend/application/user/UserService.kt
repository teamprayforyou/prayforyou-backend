package net.prayforyou.backend.application.user

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.annotation.ApplicationService
import net.prayforyou.backend.global.common.exception.ValidationException
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import net.prayforyou.backend.presenter.response.UserRankingResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    fun getUserRankingByPaging(pageable: Pageable): Page<User> {
        return userRepository.findUserRanking(pageable)
    }

    fun findAll(): MutableList<User> {
        return userRepository.findAll()
    }

    fun getUser(userNexonId: Long): User? {
        return userRepository.findByUserNexonId(userNexonId.toInt())
    }
}
