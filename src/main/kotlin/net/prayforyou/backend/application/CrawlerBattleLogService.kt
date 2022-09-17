package net.prayforyou.backend.application

import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.enums.UserType
import net.prayforyou.backend.infrastructure.crawler.webclient.client.BattleLogClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CrawlerBattleLogService(
    private val battleLogClient: BattleLogClient,
    private val saveBattleStatsService: SaveBattleStatsService
) {

    companion object {
        const val FIRST_INDEX = 0
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun saveBattleLogByUserId(user: User, battleStats: List<BattleStats>, userType: UserType) {
        val fetchBattleLog = battleLogClient.fetchBattleLog(user.userNexonId)
        if (fetchBattleLog.isEmpty()) {
            return
        }

        val battleStatsMap =
            battleStats.associateBy { it.user.userNexonId }

        // 유저닉네임 저장
        user.updateNickname(fetchBattleLog[FIRST_INDEX].user_nick!!)

        fetchBattleLog.forEach { log ->
            saveBattleStatsService.save(log, user, battleStatsMap)
        }
    }
}
