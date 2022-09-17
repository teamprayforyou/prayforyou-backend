package net.prayforyou.backend.application

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.enums.UserType
import net.prayforyou.backend.infrastructure.crawler.webclient.dto.BattleLog
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.UserProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GetUserService(
    private val userProvider: UserProvider
) {

    fun createIfNotPresentUser(firstIndexBattleLog: BattleLog, userNexonId: Int, userType: UserType): User {
        val user = userProvider.findNullableByUserNexonId(userNexonId)

        // 유저가 존재하지 않는 경우 유저를 새로 생성한다.
        return user?.updateNickname(firstIndexBattleLog.user_nick!!)
            ?: userProvider.save(User.from(firstIndexBattleLog.user_nick!!, userNexonId, userType))
    }
}