package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.CrawlerSuddenBattleService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/crawler")
class CrawlerController(
    private val crawlerSuddenBattleService: CrawlerSuddenBattleService
) {


    @PostMapping("/suddenBattle")
    fun crawlerBySuddenBattle(): String {
        crawlerSuddenBattleService.craw()
        return "OK"
    }
}