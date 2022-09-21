package net.prayforyou.backend.infrastructure.persistence.jpa.provider.user

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.UserDailyView
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserDailyViewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class UserDailyViewProvider(
    private val userDailyViewRepository: UserDailyViewRepository
) {
    fun findByUserAndTargetedAt(user: User, now: LocalDate): UserDailyView? =
        userDailyViewRepository.findByUserAndTargetedAt(user, now)

    fun save(user: User, now: LocalDate): UserDailyView =
        userDailyViewRepository.save(UserDailyView.of(user, now))

    fun findByTargetedAt(now: LocalDate): List<UserDailyView> =
        userDailyViewRepository.findAllByTargetedAt(now)
}