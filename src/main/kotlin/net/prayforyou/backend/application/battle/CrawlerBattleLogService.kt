package net.prayforyou.backend.application.battle

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.enums.UserType
import net.prayforyou.backend.infrastructure.crawler.webclient.client.BattleLogClient
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CrawlerBattleLogService(
    private val battleLogClient: BattleLogClient,
    private val saveBattleStatsService: SaveBattleStatsService,
    private val userRepository: UserRepository
) {

    private companion object {
        const val FIRST_INDEX = 0
    }

    @Transactional
    fun saveBattleLogByUserId(user: User, userType: UserType) {
        val fetchBattleLog = battleLogClient.fetchBattleLog(user)

        if (fetchBattleLog.isNotEmpty()) {
            // 유저닉네임 저장
            user.updateNickname(fetchBattleLog[FIRST_INDEX].user_nick!!)
            userRepository.save(user)

            fetchBattleLog.forEach { log ->
                saveBattleStatsService.save(log, user)
            }
        }

    }
}
