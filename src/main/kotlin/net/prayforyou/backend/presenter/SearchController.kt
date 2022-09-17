package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.SearchUserService
import net.prayforyou.backend.domain.user.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class SearchController(
    private val searchUserService: SearchUserService
) {

    @GetMapping("/search")
    fun searchUser(
        @RequestParam("nickname") nickname: String
    ): List<User> {
        // TODO: 응답값 처리
        return searchUserService.searchByNickname(nickname)
    }
}