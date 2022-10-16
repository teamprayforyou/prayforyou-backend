package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.season.GetSeasonService
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.response.RemainSeasonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/season")
class SeasonController(
    private val getSeasonService: GetSeasonService
) {

    @GetMapping("/remain")
    fun getRemainSeason(): CommonResponse<RemainSeasonResponse> {
        val remainSeason = getSeasonService.getRemainSeason()
        return CommonResponse.convert(RemainSeasonResponse(remainSeason))
    }
}