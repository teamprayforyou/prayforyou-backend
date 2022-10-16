package net.prayforyou.backend.application.user

import net.prayforyou.backend.domain.user.UserSignUpRequestEntity
import net.prayforyou.backend.global.common.exception.NotFoundDataException
import net.prayforyou.backend.global.common.exception.ValidationException
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.clan.ClanRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserSignUpRepository
import net.prayforyou.backend.presenter.request.SignUpUserRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SignUpUserService(
    private val validUserService: ValidUserService,
    private val userSignUpRepository: UserSignUpRepository,
    private val clanRepository: ClanRepository
) {

    fun signUp(request: SignUpUserRequest) {
        validUserService.checkPassword(request.password, request.rePassword)

        userSignUpRepository.save(
            UserSignUpRequestEntity.from(
                request.clanId,
                request.email,
                request.nickname,
                request.password
            )
        )

        // TODO 슬랙 메세지 전송
    }

    fun checkClanById(clanId: String) {
        clanRepository.findByClanId(clanId) ?:
        throw NotFoundDataException("클랜이 존재 하지 않습니다. 클랜 ID를 다시 입력해주세요.")
    }

    fun checkEmail(email: String) {
        validUserService.checkEmail(email)
    }

    fun userMapping(id: Long) {
        val signUp = userSignUpRepository.findByIdOrNull(id) ?: throw NotFoundDataException("회원가입 고유번호가 존재하지 않습니다.")


    }
}