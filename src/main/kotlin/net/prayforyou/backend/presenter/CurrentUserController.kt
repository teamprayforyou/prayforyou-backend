package net.prayforyou.backend.presenter

import net.prayforyou.backend.infrastructure.websocket.WebSocketEventListener
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CurrentUserController(
    private val webSocketEventListener: WebSocketEventListener
) {
    @GetMapping("/active-users")
    fun getActiveUsers(): Int {
        return webSocketEventListener.getActiveUsers() // 동접자 수 반환
    }
}