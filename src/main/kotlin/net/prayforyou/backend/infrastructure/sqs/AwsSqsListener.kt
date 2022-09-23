package net.prayforyou.backend.infrastructure.sqs

import io.awspring.cloud.messaging.listener.Acknowledgment
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import net.prayforyou.backend.application.battle.CrawlerSuddenBattleService
import net.prayforyou.backend.domain.user.enums.UserType.SUDDEN_BATTLE
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class AwsSqsListener(
    private val crawlerSuddenBattleService: CrawlerSuddenBattleService
) {

    @SqsListener(value = ["\${cloud.aws.sqs.queue.name}"], deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    fun listen(@Payload userId: String?, @Headers headers: Map<String?, String?>?, ack: Acknowledgment) {
        //수신후 삭제처리
        ack.acknowledge()

        crawlerSuddenBattleService.crawSuddenBattleLog(userId!!.toInt(), SUDDEN_BATTLE)
    }
}
