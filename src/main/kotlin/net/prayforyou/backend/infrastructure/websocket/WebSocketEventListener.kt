package net.prayforyou.backend.infrastructure.websocket

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import java.util.concurrent.atomic.AtomicInteger

@Component
class WebSocketEventListener {

    private val logger = LoggerFactory.getLogger(WebSocketEventListener::class.java)
    private val activeUsers = AtomicInteger(0) // 동접자 수를 관리하는 변수

    @EventListener
    fun handleWebSocketConnectListener(event: SessionConnectEvent) {
        val count = activeUsers.incrementAndGet() // 연결 시 동접자 수 증가
        logger.info("새로운 연결이 추가되었습니다. 현재 동접자 수: $count")
    }

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        val count = activeUsers.decrementAndGet() // 해제 시 동접자 수 감소
        logger.info("연결이 종료되었습니다. 현재 동접자 수: $count")
    }

    // 동접자 수를 반환하는 메서드
    fun getActiveUsers(): Int = activeUsers.get()
}
