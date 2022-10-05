package net.prayforyou.backend.application.battle

import net.prayforyou.backend.global.common.annotation.ApplicationService
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle.BattlePlaceRepository

@ApplicationService
class GetBattlePlaceService(
    private val battlePlaceRepository: BattlePlaceRepository
) {

    fun getBattlePlace(userNexonId: Int): List<KillDeath> {
        val findBattlePlace = battlePlaceRepository.findByUserNexonId(userNexonId)
        val killDeath = findBattlePlace.groupBy { it.battlePosition!!.battlePlaceType }
            .map { KillDeath(it.key.description,(it.key to it.value.sumOf { value -> value.kill }).second, (it.key to it.value.sumOf { value -> value.death }).second)}

        return killDeath
    }
}

class KillDeath(
    val description: String,
    val kill: Int,
    val death: Int
)
