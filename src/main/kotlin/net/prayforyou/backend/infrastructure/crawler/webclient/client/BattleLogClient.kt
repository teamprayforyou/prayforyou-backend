package net.prayforyou.backend.infrastructure.crawler.webclient.client

import net.prayforyou.backend.domain.battle.enums.BattleMapType
import net.prayforyou.backend.domain.battle.enums.BattleMapType.ALL_SUPPLY
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.infrastructure.crawler.webclient.ApiService
import net.prayforyou.backend.infrastructure.crawler.webclient.RandomProxy
import net.prayforyou.backend.infrastructure.crawler.webclient.RandomProxy.useProxy
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.BattleLog
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.BattleLogResponseDto
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.DummyRequestDto
import net.prayforyou.backend.infrastructure.crawler.webclient.setBattleLogHeaders
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattleStatsProvider
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestClientException
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger

@Component
@Transactional
class BattleLogClient(
    private val apiService: ApiService<BattleLogResponseDto>,
    private val matchClient: MatchClient,
    private val battleStatsProvider: BattleStatsProvider
) {

    val logger: Logger = Logger.getLogger(BattleLog::class.java.name)

    fun fetchBattleLog(user: User): List<BattleLog> {
        val headers = setBattleLogHeaders()
        val gameListId = matchClient.fetchGameListId(user.userNexonId)
        val battleLogList = mutableListOf<BattleLog>()
        for (i in 0..gameListId.lastIndex) {
            var gameCount = 0
            if (user.lastBattleLogId == gameListId[i]!!.toLong()) {
                break
            }
            while (true) {
                try {
                    val result = apiService.post(
                        "https://barracks.sa.nexon.com/api/BattleLog/GetBattleLog/${gameListId[i]}/${user.userNexonId}",
                        headers,
                        DummyRequestDto(),
                        BattleLogResponseDto::class.java
                    )
                    logger.log(LogRecord(Level.INFO, "배틀로그 데이터 : ${result.body!!.battleLog!!}"))
                    battleLogList.addAll(result.body!!.battleLog!!)
                    gameCount++
                    if(gameCount == 1 || user.lastBattleLogId == gameListId[i]!!.toLong()) {
                        // BattleStats에 판수 추가
                        battleStatsProvider.findByUserAndMapType(user, ALL_SUPPLY).increaseRound()
                        user.updateUserLastBattleLog(gameListId[0]!!.toLong())
                        break
                    }
                } catch (ex: Exception) {
                    when(ex) {
                        is HttpClientErrorException.TooManyRequests -> {
                            if (!useProxy) {
                                Thread.sleep(30000)
                            }
                            RandomProxy.changeIp = true
                            logger.log(LogRecord(Level.INFO, "크롤링 재시도 matchId: ${gameListId[i]} userId: ${user.userNexonId} "))
                            continue
                        }
                        is ResourceAccessException -> {
                            if (!useProxy) {
                                Thread.sleep(30000)
                            }
                            logger.log(LogRecord(Level.INFO, "ip가 차단 되었습니다"))
                            RandomProxy.changeIp = true
                            continue
                        }
                        is RestClientException -> {
                            logger.log(LogRecord(Level.INFO, "matchId: ${gameListId[i]}번째 에서 크롤링이 종료되었습니다"))
                            user.updateUserLastBattleLog(gameListId[0]!!.toLong())
                            break
                        }
                        else -> {
                            ex.printStackTrace()
                            logger.log(LogRecord(Level.SEVERE, "matchId: ${gameListId[i]} userId: ${user.userNexonId}를 크롤링 하던 도중 알 수 없는 에러가 발생하였습니다"))
                        }
                    }
                }
            }
        }
        return battleLogList
    }

}
