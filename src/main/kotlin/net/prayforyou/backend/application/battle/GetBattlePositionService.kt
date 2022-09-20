package net.prayforyou.backend.application.battle

import net.prayforyou.backend.domain.battle.BattlePosition
import net.prayforyou.backend.domain.battle.enums.BattlePlaceType.NO_POSITION
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattlePositionProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetBattlePositionService(
    private val battlePositionProvider: BattlePositionProvider
) {
    fun getBattlePositionByXandY(x: Double, y: Double): BattlePosition =
        battlePositionProvider.findAll().firstOrNull { it.isContainsBattlePosition(x, y) }
            ?: battlePositionProvider.findByPlaceType(NO_POSITION)

    fun getSupplyBattlePosition(): List<BattlePosition> {
        return battlePositionProvider.findAll()
    }
}
