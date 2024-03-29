package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.user.SignUpUserService
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.request.SignUpUserRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class SignUpUserController(
    private val signUpUserService: SignUpUserService
) {

    @PostMapping("/signup")
    fun signUp(
        @RequestBody request: SignUpUserRequest
    ): CommonResponse<Boolean> {
        signUpUserService.signUp(request)
        return CommonResponse.convert(true)
    }

    @GetMapping("/nexonId")
    fun checkClanId(
        @RequestParam("nexonId") nexonId: Int
    ): CommonResponse<Boolean> {
        signUpUserService.checkNexonId(nexonId)
        return CommonResponse.convert(true)
    }

    @GetMapping("/email")
    fun checkEmail(
        @RequestParam("input") email: String
    ): CommonResponse<Boolean> {
        signUpUserService.checkEmail(email)
        return CommonResponse.convert(true)
    }
}