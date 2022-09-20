package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.battle.GetBattlePositionService
import net.prayforyou.backend.domain.battle.BattlePosition
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/battle")
class BattleController(
    private val getBattlePositionService: GetBattlePositionService
) {

    @GetMapping("/positions")
    fun positions(): List<BattlePosition> {
        return getBattlePositionService.getSupplyBattlePosition()
    }
}
