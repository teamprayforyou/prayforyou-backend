package net.prayforyou.backend.infra.crawler.webclient.client

import net.prayforyou.backend.infra.crawler.webclient.ApiService
import net.prayforyou.backend.infra.crawler.webclient.RandomProxy
import net.prayforyou.backend.infra.crawler.webclient.dto.BattleLog
import net.prayforyou.backend.infra.crawler.webclient.dto.BattleLogResponseDto
import net.prayforyou.backend.infra.crawler.webclient.dto.DummyRequestDto
import net.prayforyou.backend.infra.crawler.webclient.setBattleLogHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestClientException
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger

@Component
class BattleLogClient(
    private val apiService: ApiService<BattleLogResponseDto>,
    private val matchClient: MatchClient
) {

    val logger: Logger = Logger.getLogger(BattleLog::class.java.name)

    fun fetchBattleLog(userId: Int): MutableList<BattleLog> {
        val headers = setBattleLogHeaders()
        val gameListId = matchClient.fetchGameListId(userId)
        val battleLogList = mutableListOf<BattleLog>()
        for (i in 0..gameListId.lastIndex) {
            if (i == 30) {
                break
            }
            while (true) {
                try {
                    val result = apiService.post(
                        "https://barracks.sa.nexon.com/api/BattleLog/GetBattleLog/${gameListId[i]}/${userId}",
                        headers,
                        DummyRequestDto(),
                        BattleLogResponseDto::class.java
                    )
                    logger.log(LogRecord(Level.INFO, "배틀로그 데이터 : ${result.body!!.battleLog!!}"))
                    battleLogList.addAll(result.body!!.battleLog!!)
                } catch (ex: Exception) {
                    when(ex) {
                        is HttpClientErrorException.TooManyRequests -> {
                            RandomProxy.changeIp = true
                            logger.log(LogRecord(Level.INFO, "크롤링 재시도 matchId: ${gameListId[i]} userId: ${userId} "))
                            continue
                        }
                        is ResourceAccessException -> {
                            logger.log(LogRecord(Level.INFO, "ip가 차단 되었습니다"))
                            RandomProxy.changeIp = true
                            continue
                        }
                        is RestClientException -> {
                            logger.log(LogRecord(Level.INFO, "gameId: ${gameListId[i]}번째 에서 크롤링이 종료되었습니다"))
                            break
                        }
                        else -> {
                            logger.log(LogRecord(Level.SEVERE, "matchId: ${gameListId[i]} userId: ${userId}를 크롤링 하던 도중 알 수 없는 에러가 발생하였습니다"))
                        }
                    }
                }
            }
        }
        return battleLogList
    }

}
