package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.battle.SearchApplicationService
import net.prayforyou.backend.application.battle.dto.BattleGunUsageDto
import net.prayforyou.backend.application.battle.dto.BattlePlaceRateDto
import net.prayforyou.backend.application.battle.dto.BattleRoundRateDto
import net.prayforyou.backend.application.battle.dto.BattleStatsDto
import net.prayforyou.backend.application.user.UserService
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.response.SearchUserResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/search")
class SearchController(
    private val searchUserService: UserService,
    private val searchApplicationService: SearchApplicationService
) {
    @GetMapping("/user")
    fun searchUser(
        @RequestParam("nickname") nickname: String
    ): CommonResponse<List<SearchUserResponse>> =
        CommonResponse.convert(
            searchUserService.searchByNickname(nickname)
                .map { SearchUserResponse.convert(it) }
        )

    @GetMapping("/{userId}/place")
    fun searchPlace(
        @PathVariable("userId") userId: Long
    ): CommonResponse<List<BattlePlaceRateDto>> =
        CommonResponse.convert(
            searchApplicationService.searchPlaceByUserId(userId)
        )

    @GetMapping("/{userId}/round")
    fun searchRound(
        @PathVariable("userId") userId: Long
    ): CommonResponse<List<BattleRoundRateDto>> =
        CommonResponse.convert(
            searchApplicationService.searchRoundByUserId(userId)
        )

    @GetMapping("/{userId}/gun")
    fun searchGun(
        @PathVariable("userId") userId: Long
    ): CommonResponse<List<BattleGunUsageDto>> =
        CommonResponse.convert(
            searchApplicationService.searchGunByUserId(userId)
        )

    @GetMapping("/{userId}/stats")
    fun searchStats(
        @PathVariable("userId") userId: Long
    ): CommonResponse<BattleStatsDto> =
        CommonResponse.convert(
            searchApplicationService.searchStats(userId)
        )
}