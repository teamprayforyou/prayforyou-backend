package net.prayforyou.backend.application.event

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class EventServiceTest {

    @Autowired
    lateinit var eventService: EventService

    @Test
    fun test() {
        eventService.process()
    }
}
