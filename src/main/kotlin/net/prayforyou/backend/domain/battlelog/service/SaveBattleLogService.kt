package net.prayforyou.backend.domain.battlelog.service

import net.prayforyou.backend.domain.battlelog.repository.BattleRepository
import net.prayforyou.backend.domain.battlelog.entity.BattleLog
import net.prayforyou.backend.domain.model.UserType
import net.prayforyou.backend.domain.user.entity.User
import net.prayforyou.backend.domain.user.repsitory.UserRepository
import net.prayforyou.backend.infra.crawler.webclient.client.BattleLogClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SaveBattleLogService(
    private val userRepository: UserRepository,
    private val battleRepository: BattleRepository,
    private val battleLogClient: BattleLogClient
) {
    fun saveBattleLogByUserId(userId: Int, userType: UserType) {
        var foundUser = userRepository.findByUserNexonId(userId)
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

        userRepository.save(foundUser)
    }
}
