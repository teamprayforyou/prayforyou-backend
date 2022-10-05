package net.prayforyou.backend.presenter.response

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.enums.UserType

data class SearchUserResponse(
    val userId: Long,
    val nickname: String,
    val userNexonId: Int,
    val clanName: String
) {
    companion object {
        fun convert(user: User): SearchUserResponse =
            SearchUserResponse(
                userId = user.id!!,
                nickname = user.nickname!!,
                userNexonId = user.userNexonId,
                clanName = user.clanId?.clanNickname
                    ?: "무소속"
            )
    }
}
