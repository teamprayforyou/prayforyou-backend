package net.prayforyou.backend.infrastructure.persistence.jpa.provider.event

import net.prayforyou.backend.application.event.EventService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class EventProviderTest {

    @Autowired
    lateinit var eventService: EventService

    @Test
    fun test() {
        eventService.process()
    }
}
