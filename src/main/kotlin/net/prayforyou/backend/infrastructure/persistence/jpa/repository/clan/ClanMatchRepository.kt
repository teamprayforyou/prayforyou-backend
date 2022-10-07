package net.prayforyou.backend.infrastructure.persistence.jpa.repository.clan

import net.prayforyou.backend.domain.match.ClanMatch
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ClanMatchRepository: JpaRepository<ClanMatch, Long> {
    fun findByMatchId(matchId: Long): ClanMatch

    @Query("select c.clanMatch from MatchUser c where c.user.userNexonId =:userNexonId")
    fun findClanMatchByUserNexonIdPageable(@Param(value = "userNexonId") userNexonId: Int, pageable: Pageable): Page<ClanMatch>

    @Query("select distinct c.clanMatch from MatchUser c where c.playClan.clanId =:clanId order by c.clanMatch.matchStartTime desc")
    fun findClanMatchByClanIdPageable(@Param(value = "clanId") clanId: Long, pageable: Pageable): Page<ClanMatch>

    @Query("select * from supply.clan_match c order by c.match_start_time desc limit 10", nativeQuery = true)
    fun findCLanMatchRecent(): List<ClanMatch>
}
