package net.prayforyou.backend.infrastructure.persistence.jpa.repository.user

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.UserDailyView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

@Repository
interface UserDailyViewRepository : JpaRepository<UserDailyView, Long> {
    fun findByUserAndTargetedAt(user: User, now: LocalDate): UserDailyView?
    fun findAllByTargetedAt(now: LocalDate): List<UserDailyView>
}
