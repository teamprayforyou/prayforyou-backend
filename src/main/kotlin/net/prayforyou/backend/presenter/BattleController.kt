package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.battle.GetBattlePlaceService
import net.prayforyou.backend.application.battle.GetBattlePositionService
import net.prayforyou.backend.application.battle.KillDeath
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.response.BattlePositionResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/battle")
class BattleController(
    private val getBattlePositionService: GetBattlePositionService,
    private val getBattlePlaceService: GetBattlePlaceService
) {
    @GetMapping("/positions")
    fun positions(): CommonResponse<List<BattlePositionResponse>> =
        CommonResponse.convert(getBattlePositionService.getSupplyBattlePosition()
            .map { BattlePositionResponse(it.polygon.toString(), it.battlePlaceType.description) })

    @GetMapping("/place")
    fun place(@RequestParam("userNexonId") userNexonId: Int): CommonResponse<List<KillDeath>> {
        return CommonResponse.convert(getBattlePlaceService.getBattlePlace(userNexonId))
    }
}
