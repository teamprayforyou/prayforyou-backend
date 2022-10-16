package net.prayforyou.backend.application.clan

import net.prayforyou.backend.domain.clan.Clan
import net.prayforyou.backend.domain.clan.enums.ClanLevel
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.clan.ClanRepository
import org.springframework.stereotype.Service

@Service
class ClanService(
    private val clanRepository: ClanRepository
) {

    fun getClanOrderByScore(levelName: ClanLevel): List<Clan> {
        return clanRepository.findClanOrderByRanking(levelName)
    }

    fun getClanOrderByCreatedAt(): List<Clan> {
        return clanRepository.findClanOrderByCreatedAt()
    }

    fun getClanById(clanId: String): Clan? {
        return clanRepository.findByClanId(clanId)
    }

    fun findAll(): List<Clan> {
        return clanRepository.findAll().filter { !it.isDeleted }
    }

}
