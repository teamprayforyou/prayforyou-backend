package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.user.UserViewService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/view")
class UserViewController(
    private val userViewService: UserViewService
) {

    @GetMapping("/daily")
    fun getDaily() {
        userViewService.getDaily()
    }

    @GetMapping("/weekly")
    fun getWeekly() {

    }
}