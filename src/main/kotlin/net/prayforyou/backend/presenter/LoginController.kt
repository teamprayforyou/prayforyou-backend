package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.user.LoginUserService
import net.prayforyou.backend.application.user.dto.LoginUserInfoDto
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.request.LoginRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class LoginController(
    private val loginUserService: LoginUserService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest
    ): CommonResponse<LoginUserInfoDto> =
        CommonResponse.convert(loginUserService.login(request))
}