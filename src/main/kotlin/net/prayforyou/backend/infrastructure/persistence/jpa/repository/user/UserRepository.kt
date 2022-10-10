package net.prayforyou.backend.infrastructure.persistence.jpa.repository.user

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.presenter.response.UserRankingResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {
    @EntityGraph(attributePaths = ["clanId"])
    fun findByUserNexonId(userId: Int): User?

    @Query("select u.userNexonId, u.nickname from User u")
    fun findAllUserNexonIdAndUserNickname(): List<User>
    fun findByNicknameContains(nickname: String): List<User>

    @Query("select u from User u where u.gameCount > 0 order by u.score desc")
    fun findUserRanking(pageable: Pageable): Page<User>
}
