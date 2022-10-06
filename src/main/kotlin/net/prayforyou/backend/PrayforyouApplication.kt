package net.prayforyou.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class PrayforyouApplication

fun main(args: Array<String>) {
    runApplication<PrayforyouApplication>(*args)
}
