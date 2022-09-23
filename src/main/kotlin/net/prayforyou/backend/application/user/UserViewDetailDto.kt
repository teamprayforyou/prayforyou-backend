package net.prayforyou.backend.application.user

import net.prayforyou.backend.domain.user.UserDailyView
import net.prayforyou.backend.domain.user.UserWeeklyView

data class UserViewDetailDto(
    val id: Long?,
    val userId: Long?,
    val nickname: String?,
    val nexonId: Int,
    val count: Int
) {
    companion object {
        fun from(view: UserDailyView): UserViewDetailDto =
            UserViewDetailDto(
                id = view.id,
                userId = view.user.id,
                count = view.count,
                nickname = view.user.nickname,
                nexonId = view.user.userNexonId
            )

        fun from(view: UserWeeklyView): UserViewDetailDto =
            UserViewDetailDto(
                id = view.id,
                userId = view.user.id,
                count = view.count,
                nickname = view.user.nickname,
                nexonId = view.user.userNexonId
            )
    }
}
