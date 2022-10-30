package net.prayforyou.backend.application.user

import net.prayforyou.backend.global.common.exception.NotFoundDataException
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import net.prayforyou.backend.presenter.request.SignUpUserRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SignUpUserService(
    private val validUserService: ValidUserService,
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) {

    fun signUp(request: SignUpUserRequest) {
        validUserService.checkPassword(request.password, request.rePassword)
        val user = userRepository.findByUserNexonId(request.userNexonId)
            ?: throw NotFoundDataException("존재 하지 않는 넥슨 아이디에요 다시 입력해주세요 😭")

        user.updateSignUp(
            request.email,
            passwordEncoder.encode(request.password)
        )
    }

    fun checkEmail(email: String) {
        validUserService.checkEmail(email)
    }

    fun checkNexonId(nexonId: Int) {
        userRepository.findByUserNexonId(nexonId) ?: throw NotFoundDataException("존재 하지 않는 넥슨 아이디에요 다시 입력해주세요 😭")
    }
}