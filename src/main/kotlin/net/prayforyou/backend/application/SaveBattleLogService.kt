package net.prayforyou.backend.application

import net.prayforyou.backend.infrastructure.persistence.jpa.provider.BattleProvider
import net.prayforyou.backend.domain.battlelog.BattleLog
import net.prayforyou.backend.domain.user.UserType
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.UserProvider
import net.prayforyou.backend.infrastructure.crawler.webclient.client.BattleLogClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SaveBattleLogService(
    private val userProvider: UserProvider,
    private val battleRepository: BattleProvider,
    private val battleLogClient: BattleLogClient
) {
    fun saveBattleLogByUserId(userId: Int, userType: UserType) {
        var foundUser = userProvider.findByUserNexonId(userId)
        val fetchBattleLog = battleLogClient.fetchBattleLog(userId)

        if (fetchBattleLog.isEmpty()) {
            return
        }

        foundUser?.updateNickname(fetchBattleLog[0].user_nick!!)

        if (foundUser == null) {
            foundUser = User(nickname = fetchBattleLog[0].user_nick!!, userNexonId = userId, userType = userType)
        }

        for (battleLog in fetchBattleLog) {
            val battleLogSave = BattleLog(
                round = battleLog.round!!.toInt(),
                eventType = battleLog.event_type!!,
                user = foundUser,
                killX = battleLog.kill_x,
                killY = battleLog.kill_y,
                eventTime = battleLog.event_time,
                weapon = battleLog.weapon,
                deathX = battleLog.death_x,
                deathY = battleLog.death_y
            )
            battleRepository.save(battleLogSave)
        }

        userProvider.save(foundUser)
    }
}
