package net.prayforyou.backend.infra.crawler.webclient.client

import net.prayforyou.backend.infra.crawler.webclient.ApiService
import net.prayforyou.backend.infra.crawler.webclient.setDummyHeaders
import org.springframework.stereotype.Component

@Component
class SuddenBattleClient(
    private val apiService: ApiService<String>
) {
    fun fetchClan(): String? {
        return apiService.get("http://suddenbattle.com/", setDummyHeaders(), String::class.java).body
    }

    fun fetchUser(clanId: String): String? {
        return apiService.get("http://suddenbattle.com/?page=1&clan_idx=${clanId}", setDummyHeaders(), String::class.java).body
    }
}
