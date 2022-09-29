package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.user.UserViewService
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.response.UserViewResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/view")
class UserViewController(
    private val userViewService: UserViewService
) {

    @GetMapping("/ranking")
    fun getView(): CommonResponse<UserViewResponse> {
        val allView = userViewService.getAllView()

        return CommonResponse.convert(
            UserViewResponse(allView.dailyView, allView.weeklyView)
        )
    }
}