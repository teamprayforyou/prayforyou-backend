package net.prayforyou.backend.infrastructure.persistence.jpa.repository.user

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.UserDailyView
import net.prayforyou.backend.domain.user.UserWeeklyView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface UserWeeklyRepository : JpaRepository<UserWeeklyView, Long> {
    fun findByUserAndTargetedAt(user: User, targetedAt: LocalDate): UserWeeklyView?
    fun findAllByTargetedAt(date: LocalDate): List<UserWeeklyView>
}
