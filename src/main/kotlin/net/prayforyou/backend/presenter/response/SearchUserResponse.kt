package net.prayforyou.backend.presenter.response

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.domain.user.enums.UserType

data class SearchUserResponse(
    val userId: Long,
    val nickname: String,
    val userNexonId: Int,
    val userType: UserType
) {
    companion object {
        fun convert(user: User): SearchUserResponse =
            SearchUserResponse(
                userId = user.id!!,
                nickname = user.nickname!!,
                userNexonId = user.userNexonId,
                userType = user.userType
            )
    }
}