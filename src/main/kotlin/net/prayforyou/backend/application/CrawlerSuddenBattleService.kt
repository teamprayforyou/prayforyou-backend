package net.prayforyou.backend.application

import mu.KotlinLogging
import net.prayforyou.backend.domain.user.enums.UserType
import net.prayforyou.backend.global.common.ApplicationService
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.BattleStatsProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.UserProvider
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@ApplicationService
class CrawlerSuddenBattleService(
    private val userProvider: UserProvider,
    private val crawlerBattleLogService: CrawlerBattleLogService,
    private val battleStatsProvider: BattleStatsProvider
) {

    private companion object {
        val logger = KotlinLogging.logger {}
        const val CHUNK_SIZE = 50
    }

    fun craw(userType: UserType = UserType.SUDDEN_BATTLE) {
        logger.info { " START CRAWLING ${LocalDateTime.now()} " }

        userProvider.findAll().chunked(CHUNK_SIZE)
            .forEachIndexed { index, user ->
                logger.info { " CRAWLING... Time :  ${LocalDateTime.now()},  index : $index" }
                val battleStats = battleStatsProvider.findAllByUserIdIn(user.map { it.id!! })
                crawlerBattleLogService.saveBattleLogByUserId(user, battleStats, userType)
            }
    }
}