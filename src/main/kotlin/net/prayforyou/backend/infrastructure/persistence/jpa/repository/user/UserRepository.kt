package net.prayforyou.backend.infrastructure.persistence.jpa.repository.user

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.presenter.response.UserRankingResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {
    fun findByUserNexonId(userId: Int): User?
    fun findByNicknameContains(nickname: String): List<User>

    @Query("select new net.prayforyou.backend.presenter.response.UserRankingResponse(u.userNexonId, u.nickname, u.clanId.clanMarkUrl, u.winCount, u.gameCount, u.winLoosePercent, u.killDeath, u.killPerGame, u.score) from User u order by u.score desc")
    fun findUserRanking(pageable: Pageable): Page<UserRankingResponse>
}
