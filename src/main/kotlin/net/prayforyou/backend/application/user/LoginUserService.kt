package net.prayforyou.backend.application.user

import net.prayforyou.backend.application.user.dto.LoginUserInfoDto
import net.prayforyou.backend.global.common.exception.NotFoundDataException
import net.prayforyou.backend.global.common.exception.ValidationException
import net.prayforyou.backend.global.util.JwtTokenUtil
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import net.prayforyou.backend.presenter.request.LoginRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LoginUserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenUtil: JwtTokenUtil
) {

    fun login(request: LoginRequest): LoginUserInfoDto {
        val user = userRepository.findByEmail(request.email)
            ?: throw NotFoundDataException(message = "존재하지 않는 이메일이에요 😭")

        val matches = passwordEncoder.matches(request.password, user.password)
        if (!matches) {
            throw ValidationException(message = "비밀번호가 일치 하지않아요 😭")
        }

        val token = jwtTokenUtil.create(user.id!!)

        return LoginUserInfoDto.from(user, token)
    }
}