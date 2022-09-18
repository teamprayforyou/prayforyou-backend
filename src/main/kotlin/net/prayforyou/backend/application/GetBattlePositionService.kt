package net.prayforyou.backend.application

import net.prayforyou.backend.domain.battle.BattlePosition
import net.prayforyou.backend.domain.battle.enums.BattlePlaceType
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.BattlePositionProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException

@Service
@Transactional(readOnly = true)
class GetBattlePositionService(
    private val battlePositionProvider: BattlePositionProvider
) {
    fun getBattlePositionByXandY(x: Double, y: Double): BattlePlaceType =
        battlePositionProvider.findAll().firstOrNull { it.isContainsBattlePosition(x, y) }
            ?.battlePlaceType ?: BattlePlaceType.NO_POSITION
}
