package net.prayforyou.backend.infrastructure.persistence.jpa.repository.user

import net.prayforyou.backend.domain.user.UserWeeklyView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserWeeklyRepository : JpaRepository<UserWeeklyView, Long> {
}