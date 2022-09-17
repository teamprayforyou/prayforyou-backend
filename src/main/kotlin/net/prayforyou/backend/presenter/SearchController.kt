package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.SearchService
import net.prayforyou.backend.application.dto.BattleGunUsageDto
import net.prayforyou.backend.application.dto.BattlePlaceRateDto
import net.prayforyou.backend.application.dto.BattleRoundRateDto
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.UserProvider
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/search")
class SearchController(
    private val searchService: SearchService
) {

    @GetMapping("/user")
    fun searchUser(
        @RequestParam("nickname") nickname: String
    ): CommonResponse<List<User>> =
        CommonResponse.convert(
            searchService.searchByNickname(nickname)
        )

    @GetMapping("/place")
    fun searchPlace(
        @PathVariable("userId") userId: Int
    ): CommonResponse<List<BattlePlaceRateDto>> =
        CommonResponse.convert(
            searchService.searchPlaceByUserId(userId)
        )

    @GetMapping("/round")
    fun searchRound(
        @PathVariable("userId") userId: Int
    ): CommonResponse<List<BattleRoundRateDto>> =
        CommonResponse.convert(
            searchService.searchRoundByUserId(userId)
        )

    @GetMapping("/gun")
    fun searchGun(
        @PathVariable("userId") userId: Int
    ): CommonResponse<List<BattleGunUsageDto>> =
        CommonResponse.convert(
            searchService.searchGunByUserId(userId)
        )
}