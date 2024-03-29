package net.prayforyou.backend.infrastructure.persistence.jpa.repository.user

import net.prayforyou.backend.domain.user.UserSignUpRequestEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserSignUpRepository : JpaRepository<UserSignUpRequestEntity, Long> {
}