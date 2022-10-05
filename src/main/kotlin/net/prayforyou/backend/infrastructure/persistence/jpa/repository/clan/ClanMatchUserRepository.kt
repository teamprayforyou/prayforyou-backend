package net.prayforyou.backend.infrastructure.persistence.jpa.repository.clan

import net.prayforyou.backend.domain.match.MatchUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ClanMatchUserRepository: JpaRepository<MatchUser, Long> {
    @Query("select * from match_user m left join clan_match cm on m.clan_match_id = cm.id where cm.match_id =:matchId and m.play_clan_id =:clanId", nativeQuery = true)
    fun findMatchUserList(@Param(value = "matchId") matchId: Long, @Param(value = "clanId") clanId: Long): List<MatchUser>
}
