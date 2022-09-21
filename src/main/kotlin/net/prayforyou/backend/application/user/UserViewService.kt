package net.prayforyou.backend.application.user

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.UserDailyView
import net.prayforyou.backend.domain.user.UserWeeklyView
import net.prayforyou.backend.global.util.DateUtil
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserDailyViewProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserWeeklyViewProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class UserViewService(
    private val userProvider: UserProvider,
    private val userDailyProvider: UserDailyViewProvider,
    private val userWeeklyViewProvider: UserWeeklyViewProvider
) {

    fun getDaily(user: User, now: LocalDate): UserDailyView =
        userDailyProvider.findByUserAndTargetedAt(user, now)
            ?: userDailyProvider.save(user, now)

    fun getWeekly(user: User, now: LocalDate): UserWeeklyView {
        val date = DateUtil.getMondayDate(now)
        return userWeeklyViewProvider.findByUserAndTargetedAt(user, date)
            ?: userWeeklyViewProvider.save(user, date)
    }

    fun updateView(userId: Long, now: LocalDate = LocalDate.now()) {
        val user = userProvider.findByUserId(userId)
        val dailyView = getDaily(user, now)
        val weeklyView = getWeekly(user, now)

        dailyView.updateViewCount()
        weeklyView.updateViewCount()
    }
}
