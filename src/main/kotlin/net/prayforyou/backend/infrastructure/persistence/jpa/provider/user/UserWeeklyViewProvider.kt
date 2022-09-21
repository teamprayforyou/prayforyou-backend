package net.prayforyou.backend.infrastructure.persistence.jpa.provider.user

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.UserDailyView
import net.prayforyou.backend.domain.user.UserWeeklyView
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserWeeklyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class UserWeeklyViewProvider(
    private val userWeeklyRepository: UserWeeklyRepository
) {

    fun findByUserAndTargetedAt(user: User, date: LocalDate): UserWeeklyView? =
        userWeeklyRepository.findByUserAndTargetedAt(user, date)

    fun save(user: User, date: LocalDate): UserWeeklyView =
        userWeeklyRepository.save(UserWeeklyView.of(user, date))

    fun findByTargetedAt(date: LocalDate): List<UserWeeklyView> =
        userWeeklyRepository.findAllByTargetedAt(date)
}