package net.prayforyou.backend.infrastructure.persistence.jpa.repository.user

import net.prayforyou.backend.domain.user.UserJson
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserJsonRepository: JpaRepository<UserJson, Long> {
    fun findAllByTodoIsTrueOrderByCreatedAtDesc(): List<UserJson>
}
