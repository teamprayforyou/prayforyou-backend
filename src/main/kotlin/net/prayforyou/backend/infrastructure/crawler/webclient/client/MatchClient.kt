package net.prayforyou.backend.infrastructure.crawler.webclient.client

import net.prayforyou.backend.infrastructure.crawler.webclient.ApiService
import net.prayforyou.backend.infrastructure.crawler.webclient.RandomProxy
import net.prayforyou.backend.infrastructure.crawler.webclient.RandomProxy.changeIp
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.GetMatchListRequestDto
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.GetMatchListResponseDto
import net.prayforyou.backend.infrastructure.crawler.webclient.setMatchHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestClientException
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger

@Component
class MatchClient(
    private val apiService: ApiService<GetMatchListResponseDto>
) {

    val logger: Logger = Logger.getLogger(MatchClient::class.java.name)

    fun fetchGameListId(userId: Int): List<String?> {
        val headers = setMatchHeaders()

        var num = "0"
        val resultList = mutableListOf<String?>()
        var post: ResponseEntity<GetMatchListResponseDto>

        while (true) {
            try {
                // 가져온 3보급 창고 경기 수가 100개가 되면 종료
                if (resultList.size == 100) {
                    break
                }
                post = postMatchList(headers, num, userId)
                num = post.body!!.message
                resultList.addAll(post.body!!.result
                    .filter { it.map_name.equals("제3보급창고") }
                    .filter { it.match_name.equals("클랜매치") || it.match_name.equals("용병매치") }
                    .map { it.match_key })

            } catch (ex: Exception) {
                when(ex) {
                    is HttpClientErrorException.TooManyRequests -> {
                        if (!RandomProxy.useProxy) {
                            Thread.sleep(30000)
                        }
                        changeIp = true
                        logger.log(LogRecord(Level.INFO, "크롤링 재시도 matchId: ${num} userId: ${userId} "))
                        continue
                    }
                    is ResourceAccessException -> {
                        if (!RandomProxy.useProxy) {
                            Thread.sleep(30000)
                        }
                        logger.log(LogRecord(Level.INFO, "ip가 차단 되었습니다"))
                        changeIp = true
                        continue
                    }
                    is RestClientException -> {
                        logger.log(LogRecord(Level.INFO, "gameId: ${num}번째 에서 크롤링이 종료되었습니다"))
                        break
                    }
                    else -> {
                        logger.log(LogRecord(Level.SEVERE, "matchId: ${num} userId: ${userId}를 크롤링 하던 도중 알 수 없는 에러가 발생하였습니다"))
                    }
                }
            }
        }

        return resultList
    }

    private fun postMatchList(
        headers: LinkedMultiValueMap<String, String>,
        num: String,
        userId: Int
    ): ResponseEntity<GetMatchListResponseDto> {
        return requestMatchList(headers, num, userId)

    }

    private fun requestMatchList(
        headers: LinkedMultiValueMap<String, String>,
        num: String,
        userId: Int
    ) = apiService.post(
        "https://barracks.sa.nexon.com/api/Match/GetMatchList/",
        headers,
        GetMatchListRequestDto(seq_no = num.toLong(), user_nexon_sn = userId.toString()),
        GetMatchListResponseDto::class.java
    )
}
