package net.prayforyou.backend.application.user

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.annotation.ApplicationService
import net.prayforyou.backend.global.common.exception.ValidationException
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import net.prayforyou.backend.presenter.response.UserRankingResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
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

    // 페이지네이션 처리
    fun getUserRankingByPaging(pageable: Pageable): Page<User> {
        // 페이지 0은 그대로 사용하고, 페이지 1부터는 인덱스를 +1로 처리
        val adjustedPage = if (pageable.pageNumber == 0) {
            pageable.pageNumber
        } else {
            pageable.pageNumber + 1 // 페이지 1부터는 +1
        }

        // 새로운 pageable을 사용하여 UserRepository에서 호출
        val adjustedPageable = PageRequest.of(adjustedPage, pageable.pageSize, pageable.sort)

        return userRepository.findUserRanking(adjustedPageable)
    }

    fun findAll(): MutableList<User> {
        return userRepository.findAll()
    }

    fun getUser(userNexonId: Long): User? {
        return userRepository.findByUserNexonId(userNexonId.toInt())
    }
}
