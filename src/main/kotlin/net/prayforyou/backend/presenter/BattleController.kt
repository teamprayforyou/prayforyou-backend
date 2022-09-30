package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.battle.GetBattlePositionService
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.response.BattlePositionResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/battle")
class BattleController(
    private val getBattlePositionService: GetBattlePositionService
) {
    @GetMapping("/positions")
    fun positions(): CommonResponse<List<BattlePositionResponse>> =
        CommonResponse.convert(getBattlePositionService.getSupplyBattlePosition()
            .map { BattlePositionResponse(it.polygon.toString(), it.battlePlaceType.description) })
}
