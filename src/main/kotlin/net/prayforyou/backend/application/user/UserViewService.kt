package net.prayforyou.backend.application.user

import net.prayforyou.backend.application.user.dto.UserViewDetailDto
import net.prayforyou.backend.application.user.dto.UserViewDto
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.UserDailyView
import net.prayforyou.backend.domain.user.UserWeeklyView
import net.prayforyou.backend.global.common.exception.NotFoundDataException
import net.prayforyou.backend.global.util.DateUtil
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserDailyViewProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserWeeklyViewProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class UserViewService(
    private val userRepository: UserRepository,
    private val userDailyProvider: UserDailyViewProvider,
    private val userWeeklyViewProvider: UserWeeklyViewProvider
) {

    fun getDaily(user: User, now: LocalDate): UserDailyView =
        userDailyProvider.findByUserAndTargetedAt(user, now)
            ?: userDailyProvider.save(user, now)

    fun getWeekly(user: User): UserWeeklyView {
        val date = DateUtil.getMondayDate()
        return userWeeklyViewProvider.findByUserAndTargetedAt(user, date)
            ?: userWeeklyViewProvider.save(user, date)
    }

    fun updateView(userId: Long, now: LocalDate = LocalDate.now()) {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundDataException("유저를 찾을 수 없습니다.")
        val dailyView = getDaily(user, now)
        val weeklyView = getWeekly(user)

        dailyView.updateViewCount()
        weeklyView.updateViewCount()
    }

    fun getAllView(now: LocalDate = LocalDate.now()): UserViewDto {
        val dailyView =
            userDailyProvider.findByTargetedAt(now)
                .sortedByDescending { it.count }
                .take(10)
                .map { UserViewDetailDto.from(it) }

        val weeklyView =
            userWeeklyViewProvider.findByTargetedAt(DateUtil.getMondayDate())
                .sortedByDescending { it.count }
                .take(10)
                .map { UserViewDetailDto.from(it) }

        return UserViewDto(
            weeklyView = weeklyView,
            dailyView = dailyView
        )
    }
}
