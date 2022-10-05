package net.prayforyou.backend.infrastructure.persistence.jpa.provider.event

import net.prayforyou.backend.domain.event.Event
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.event.EventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class EventProvider(
    private val eventRepository: EventRepository
) {

    fun findTodoEvents(): List<Event> {
        return eventRepository.findByTodoIsTrue()
    }
}
