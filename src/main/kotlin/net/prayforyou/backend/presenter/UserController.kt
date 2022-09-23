package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.user.UserService
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.response.UserResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{userId}")
    fun getUser(
        @PathVariable("userId") userId: Long
    ): CommonResponse<UserResponse> {
        val user = userService.getByUserId(userId)
        return CommonResponse.convert(UserResponse.from(user))
    }
}