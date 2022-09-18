package net.prayforyou.backend.application.battle

import net.prayforyou.backend.domain.battle.enums.BattlePlaceType
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattlePositionProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetBattlePositionService(
    private val battlePositionProvider: BattlePositionProvider
) {
    fun getBattlePositionByXandY(x: Double, y: Double): BattlePlaceType =
        battlePositionProvider.findAll().firstOrNull { it.isContainsBattlePosition(x, y) }
            ?.battlePlaceType ?: BattlePlaceType.NO_POSITION
}
