package net.prayforyou.backend.application.user

import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserDailyProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserWeeklyProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class UserViewService(
    private val userDailyProvider: UserDailyProvider,
    private val userWeeklyProvider: UserWeeklyProvider
) {
    // 후에 REDIS 이용해서
    fun getDaily(now: LocalDateTime = LocalDateTime.now()) {
        userDailyProvider

    }

    // 후에 REDIS 이용해서
    fun getWeekly(now: LocalDateTime = LocalDateTime.now()) {

    }
}