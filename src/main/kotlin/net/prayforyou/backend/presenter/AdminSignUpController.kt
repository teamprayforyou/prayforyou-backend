package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.user.SignUpUserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class AdminSignUpController(
    private val signUpUserService: SignUpUserService
) {

    @GetMapping("/user-signup/{id}")
    fun userMapping(
        @PathVariable("id") id: Long
    ) {
        signUpUserService.userMapping(id)
    }
}