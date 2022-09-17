package net.prayforyou.backend.application

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.UserProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SaveUserService(
    private val userProvider: UserProvider
) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun updateNickname(user: User, userNick: String): User =
        userProvider.save(user.updateNickname(userNick))
}