package net.prayforyou.backend.application

import net.prayforyou.backend.domain.battle.BattlePosition
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.BattlePositionProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException

@Service
@Transactional(readOnly = true)
class GetBattlePositionService(
    private val battlePositionProvider: BattlePositionProvider
) {

    fun getBattlePositionByXandY(x: Int, y: Int): BattlePosition {
        val battlePositions = battlePositionProvider.findAll()
        return try {
            battlePositions.first { it.isContainsBattlePosition(x, y) }
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException("해당 좌표에 대한 배틀 포지션이 없습니다")
        }

    }
}
