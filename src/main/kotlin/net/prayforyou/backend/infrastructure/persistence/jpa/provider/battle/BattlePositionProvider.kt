package net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle

import net.prayforyou.backend.domain.battle.BattlePosition
import net.prayforyou.backend.domain.battle.enums.BattlePlaceType
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle.BattlePositionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BattlePositionProvider(
    private val battlePositionRepository: BattlePositionRepository
) {
    fun findAll(): List<BattlePosition> = battlePositionRepository.findAll()

    fun findByPlaceType(type: BattlePlaceType): BattlePosition = battlePositionRepository.findByBattlePlaceType(type)
        ?: throw NoSuchElementException("타입 = ${type.name}에 해당하는 배틀 포지션이 없습니다")
}
