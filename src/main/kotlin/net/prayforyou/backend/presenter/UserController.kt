package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.battle.SearchUserBattleStatsService
import net.prayforyou.backend.application.battle.dto.SearchUserBattleStatsDto
import net.prayforyou.backend.application.user.UserService
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.response.SearchUserResponse
import net.prayforyou.backend.presenter.response.UserResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val searchUserBattleStatsService: SearchUserBattleStatsService,
) {

    @GetMapping("/{userId}/battle")
    fun getUserBattle(
        @PathVariable("userId") userId: Long
    ): CommonResponse<SearchUserBattleStatsDto> =
        CommonResponse.convert(
            searchUserBattleStatsService.getBattleStatsByUserId(userId)
        )

    @GetMapping("/search")
    fun searchUser(
        @RequestParam("nickname") nickname: String
    ): CommonResponse<List<SearchUserResponse>> =
        CommonResponse.convert(
            userService.searchByNickname(nickname)
                .map { SearchUserResponse.convert(it) }
        )
}