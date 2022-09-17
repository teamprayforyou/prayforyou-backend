package net.prayforyou.backend.infrastructure.crawler.webclient.client

import net.prayforyou.backend.infrastructure.crawler.webclient.ApiService
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.ClanUserRequestDto
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.ClanUserResponseDto
import net.prayforyou.backend.infrastructure.crawler.webclient.setDummyHeaders
import org.springframework.stereotype.Component

@Component
class ClanUserClient(
    private val apiService: ApiService<ClanUserResponseDto>,
) {

    fun fetchUserInfoIdListByClanId(clanIdList: List<String>): List<Int?> {
        val clanUserList: MutableList<List<Int?>> = mutableListOf()
        for (clanId in clanIdList) {
            val post = apiService.post(
                "https://barracks.sa.nexon.com/api/ClanHome/GetClanUserList",
                setDummyHeaders(),
                ClanUserRequestDto(clanId),
                ClanUserResponseDto::class.java
            )
            val resultClanUserList = post.body?.resultClanUserList?.map { it?.user_nexon_sn }
                ?: continue
            clanUserList.add(resultClanUserList)
        }

        return clanUserList.flatten()
    }
}
