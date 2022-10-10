package net.prayforyou.backend.infrastructure.persistence.jpa.repository.clan

import net.prayforyou.backend.domain.clan.Clan
import net.prayforyou.backend.domain.clan.enums.ClanLevel
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ClanRepository: JpaRepository<Clan, Long> {
    fun findByClanId(clanId: Long): Clan?
    fun findByClanName(clanName: String): Clan?
    @Query("select c from Clan c where c.clanLevel=:levelName order by c.score desc")
    fun findClanOrderByRanking(@Param("levelName") levelName: ClanLevel): List<Clan>

    @Query("select c from Clan c order by c.createdAt desc")
    fun findClanOrderByCreatedAt(): List<Clan>
}
