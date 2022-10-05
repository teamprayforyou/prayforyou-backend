package net.prayforyou.backend.infrastructure.persistence.jpa.repository.event

import net.prayforyou.backend.domain.event.Event
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository: JpaRepository<Event, Long> {
    fun findByTodoIsTrue(): List<Event>
}
