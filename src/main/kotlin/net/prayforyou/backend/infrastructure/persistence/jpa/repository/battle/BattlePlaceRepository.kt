package net.prayforyou.backend.infrastructure.persistence.jpa.repository.battle

import net.prayforyou.backend.domain.battle.BattlePlace
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BattlePlaceRepository : JpaRepository<BattlePlace, Long> {
    @Query("select p from BattlePlace p where p.user.userNexonId =:userNexonId")
    fun findByUserNexonId(@Param("userNexonId") userNexonId: Int): List<BattlePlace>
}
