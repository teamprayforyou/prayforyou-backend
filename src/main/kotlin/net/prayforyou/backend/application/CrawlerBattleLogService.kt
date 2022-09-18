package net.prayforyou.backend.application

import mu.KotlinLogging
import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.enums.UserType
import net.prayforyou.backend.infrastructure.crawler.webclient.client.BattleLogClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class CrawlerBattleLogService(
    private val battleLogClient: BattleLogClient,
    private val saveBattleStatsService: SaveBattleStatsService
) {

    private companion object {
        const val FIRST_INDEX = 0
        val logger = KotlinLogging.logger {}

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun saveBattleLogByUserId(user: List<User>, battleStats: List<BattleStats>, userType: UserType) {
        user.map { targetUser ->
            logger.info { "START USER : userId : ${targetUser.id}, time :${LocalDateTime.now()}" }

            val fetchBattleLog = battleLogClient.fetchBattleLog(targetUser.userNexonId)

            if (fetchBattleLog.isNotEmpty()) {
                val battleStatsMap =
                    battleStats.associateBy { it.user.userNexonId }

                // 유저닉네임 저장
                targetUser.updateNickname(fetchBattleLog[FIRST_INDEX].user_nick!!)

                fetchBattleLog.forEach { log ->
                    saveBattleStatsService.save(log, targetUser, battleStatsMap)
                }
            }
        }
    }
}
