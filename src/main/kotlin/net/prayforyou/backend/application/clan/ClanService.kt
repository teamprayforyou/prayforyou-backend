package net.prayforyou.backend.application.clan

import net.prayforyou.backend.domain.clan.Clan
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.clan.ClanRepository
import org.springframework.stereotype.Service

@Service
class ClanService(
    private val clanRepository: ClanRepository
) {

    fun getClanOrderByScore(): List<Clan> {
        return clanRepository.findClanOrderByRanking()
    }

    fun getClanById(clanId: Long): Clan? {
        return clanRepository.findByClanId(clanId)
    }

    fun findAll(): MutableList<Clan> {
        return clanRepository.findAll()
    }

}
