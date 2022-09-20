package net.prayforyou.backend.application.battle

import mu.KotlinLogging
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.enums.UserType
import net.prayforyou.backend.global.common.annotation.ApplicationService
import net.prayforyou.backend.infrastructure.crawler.parser.SuddenBattleParser
import net.prayforyou.backend.infrastructure.crawler.webclient.client.ClanUserClient
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.battle.BattleStatsProvider
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserProvider
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@ApplicationService
class CrawlerSuddenBattleService(
    private val userProvider: UserProvider,
    private val crawlerBattleLogService: CrawlerBattleLogService,
    private val suddenBattleParser: SuddenBattleParser,
    private val clanUserClient: ClanUserClient
) {

    private companion object {
        val logger = KotlinLogging.logger {}
        const val CHUNK_SIZE = 50
    }

    fun crawSuddenBattleLog(userType: UserType = UserType.SUDDEN_BATTLE) {
        logger.info { " START CRAWLING ${LocalDateTime.now()} " }

        userProvider.findAll().chunked(CHUNK_SIZE)
            .forEachIndexed { index, user ->
                logger.info { " CRAWLING... Time :  ${LocalDateTime.now()},  index : $index" }
                crawlerBattleLogService.saveBattleLogByUserId(user, userType)
            }

        logger.info { " END CRAWLING ${LocalDateTime.now()} " }
    }

    fun crawSuddenBattleAllUser(userType: UserType = UserType.SUDDEN_BATTLE) {
        val userInfoIdList = clanUserClient
            .fetchUserInfoIdListByClanId(suddenBattleParser.parseClanId())

        val userList: MutableList<User> = mutableListOf()
        for (userNexonId in userInfoIdList) {
            userList.add(User.from(userNexonId = userNexonId!!.toInt(), userType = userType))
        }

        userProvider.saveAllUser(userList)
    }

}
