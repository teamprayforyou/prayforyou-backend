package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.battle.SearchApplicationService
import net.prayforyou.backend.application.battle.dto.BattleGunUsageDto
import net.prayforyou.backend.application.battle.dto.BattlePlaceRateDto
import net.prayforyou.backend.application.battle.dto.BattleRoundRateDto
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.response.SearchUserResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/search")
class SearchController(
    private val searchApplicationService: SearchApplicationService
) {

    @GetMapping("/user")
    fun searchUser(
        @RequestParam("nickname") nickname: String
    ): CommonResponse<List<SearchUserResponse>> =
        CommonResponse.convert(
            searchApplicationService.searchByNickname(nickname)
                .map { SearchUserResponse.convert(it) }
        )

    @GetMapping("{userId}/place")
    fun searchPlace(
        @PathVariable("userId") userId: Int
    ): CommonResponse<List<BattlePlaceRateDto>> =
        CommonResponse.convert(
            searchApplicationService.searchPlaceByUserId(userId)
        )

    @GetMapping("{userId}/round")
    fun searchRound(
        @PathVariable("userId") userId: Int
    ): CommonResponse<List<BattleRoundRateDto>> =
        CommonResponse.convert(
            searchApplicationService.searchRoundByUserId(userId)
        )

    @GetMapping("{userId}/gun")
    fun searchGun(
        @PathVariable("userId") userId: Int
    ): CommonResponse<List<BattleGunUsageDto>> =
        CommonResponse.convert(
            searchApplicationService.searchGunByUserId(userId)
        )
}