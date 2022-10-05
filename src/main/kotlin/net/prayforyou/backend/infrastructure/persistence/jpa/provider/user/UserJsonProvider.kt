package net.prayforyou.backend.infrastructure.persistence.jpa.provider.user

import net.prayforyou.backend.domain.user.UserJson
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserJsonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserJsonProvider(
    private val userJsonRepository: UserJsonRepository
) {

    fun findTodoEvents(): List<UserJson> {
        return userJsonRepository.findAllByTodoIsTrueOrderByCreatedAtDesc()
    }
}
