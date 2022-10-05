package net.prayforyou.backend.application.user.dto

import net.prayforyou.backend.domain.user.UserDailyView
import net.prayforyou.backend.domain.user.UserWeeklyView
import net.prayforyou.backend.domain.user.enums.UserType

data class UserViewDetailDto(
    val id: Long?,
    val userId: Long?,
    val nickname: String?,
    val userNexonId: Int,
    val count: Int
) {
    companion object {
        fun from(view: UserDailyView): UserViewDetailDto =
            UserViewDetailDto(
                id = view.id,
                userId = view.user.id,
                count = view.count,
                nickname = view.user.nickname,
                userNexonId = view.user.userNexonId,
            )

        fun from(view: UserWeeklyView): UserViewDetailDto =
            UserViewDetailDto(
                id = view.id,
                userId = view.user.id,
                count = view.count,
                nickname = view.user.nickname,
                userNexonId = view.user.userNexonId,
            )
    }
}
