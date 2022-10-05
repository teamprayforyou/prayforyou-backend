package net.prayforyou.backend.infrastructure.persistence.jpa.repository.clan

import net.prayforyou.backend.domain.clan.Clan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ClanRepository: JpaRepository<Clan, Long> {
    fun findByClanId(clanId: Long): Clan?
    fun findByClanName(clanName: String): Clan?
    @Query("select * from Clan c order by c.score desc", nativeQuery = true)
    fun findClanOrderByRanking(): List<Clan>
}
