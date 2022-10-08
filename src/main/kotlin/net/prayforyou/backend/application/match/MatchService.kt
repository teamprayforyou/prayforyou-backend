package net.prayforyou.backend.application.match

import net.prayforyou.backend.domain.match.ClanMatch
import net.prayforyou.backend.domain.match.MatchUser
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.annotation.ApplicationService
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.clan.ClanMatchRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.clan.ClanMatchUserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@ApplicationService
class MatchService(
    private val matchUserRepository: ClanMatchUserRepository,
    private val matchRepository: ClanMatchRepository
) {
    fun getMatchDataByUserNexonId(userNexonId: Long, pageable: Pageable): Page<ClanMatch> {
        return matchRepository.findClanMatchByUserNexonIdPageable(userNexonId.toInt(), pageable)
    }

    fun getMatchDataByClanId(clanId: Long, pageable: Pageable): Page<ClanMatch> {
        return matchRepository.findClanMatchByClanIdPageable(clanId, pageable)
    }

    fun getMatchUsers(matchId: Long, clanId: Long): List<MatchUser> {
        return matchUserRepository.findMatchUserList(matchId, clanId)
    }

    fun getMatchUser(user: User, match: ClanMatch): MatchUser {
        return matchUserRepository.findByClanMatchAndUser(match, user)
    }

    fun getMatchDetail(matchId: Long): ClanMatch {
        return matchRepository.findByMatchId(matchId)
    }

    fun getRecentMatch(): List<ClanMatch> {
        return matchRepository.findCLanMatchRecent()
    }
}
