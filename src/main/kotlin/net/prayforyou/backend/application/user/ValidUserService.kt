package net.prayforyou.backend.application.user

import net.prayforyou.backend.global.common.exception.ValidationException
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ValidUserService(
    private val userRepository: UserRepository
) {

    fun checkPassword(firstPassword: String, secondPassword: String) {
        if (firstPassword != secondPassword) {
            throw ValidationException("재입력한 비밀번호가 일치하지 않습니다 다시 확인해주세요")
        }
    }

    fun checkEmail(email: String) {
        val user = userRepository.findByEmail(email)
        user?.let { throw ValidationException("이미 존재하는 이메일 입니다.") }
    }
}