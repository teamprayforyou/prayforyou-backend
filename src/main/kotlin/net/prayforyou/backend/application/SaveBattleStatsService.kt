package net.prayforyou.backend.application

import net.prayforyou.backend.domain.battle.BattleStats
import net.prayforyou.backend.domain.user.enums.UserType
import net.prayforyou.backend.infrastructure.crawler.webclient.client.BattleLogClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SaveBattleStatsService(
    private val getUserService: GetUserService,
    private val battleLogClient: BattleLogClient
) {

    companion object {
        const val FIRST_INDEX = 0
    }

    fun saveBattleLogByUserId(userNexonId: Int, userType: UserType) {
        val fetchBattleLog = battleLogClient.fetchBattleLog(userNexonId)

        if (fetchBattleLog.isEmpty()) {
            return
        }

        val firstIndexBattleLog = fetchBattleLog[FIRST_INDEX]

        val user =
            getUserService.createIfNotPresentUser(firstIndexBattleLog, userNexonId, userType)



        // TODO 배틀로그 저장하는 부분
        fetchBattleLog.forEach {
            BattleStats.from(it, user)
        }
    }
}
