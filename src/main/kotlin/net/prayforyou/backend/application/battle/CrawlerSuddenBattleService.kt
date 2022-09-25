package net.prayforyou.backend.application.battle

import mu.KotlinLogging
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.enums.UserType
import net.prayforyou.backend.global.common.annotation.ApplicationService
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@ApplicationService
class CrawlerSuddenBattleService(
    private val userRepository: UserRepository,
    private val crawlerBattleLogService: CrawlerBattleLogService,
) {

    private companion object {
        val logger = KotlinLogging.logger {}
    }

    fun crawSuddenBattleLog(userNexonId: Int, userType: UserType = UserType.SUDDEN_BATTLE) {
        logger.info { " START CRAWLING ${LocalDateTime.now()} " }

        val user = userRepository.findByUserNexonId(userNexonId)
            ?: userRepository.saveAndFlush(User.from(userNexonId = userNexonId, userType = userType))

        crawlerBattleLogService.saveBattleLogByUserId(user, userType)

        logger.info { " END CRAWLING ${LocalDateTime.now()} " }
    }
}
