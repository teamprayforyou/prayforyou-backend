package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.banner.GetBannerService
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.response.BannerResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/banner")
class BannerController(
    private val getBannerService: GetBannerService
) {

    @GetMapping("/")
    fun getBanner(
        @RequestParam("type") type: String
    ): CommonResponse<BannerResponse> =
        CommonResponse.convert(
            BannerResponse.convert(getBannerService.getBannerByDateTIme())
        )
}