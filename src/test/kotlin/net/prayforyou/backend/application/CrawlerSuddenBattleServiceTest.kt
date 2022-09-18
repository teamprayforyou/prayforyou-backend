package net.prayforyou.backend.application

import net.prayforyou.backend.infrastructure.persistence.jpa.provider.UserProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CrawlerSuddenBattleServiceTest {

    @Autowired
    lateinit var userProvider: UserProvider

    @Test
    fun test() {

        val test = userProvider.findAll()
        println(test)
        println(test.count())
    }
}