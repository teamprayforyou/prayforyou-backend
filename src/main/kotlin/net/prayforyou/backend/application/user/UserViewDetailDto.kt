package net.prayforyou.backend.application.user

import net.prayforyou.backend.domain.user.UserDailyView
import net.prayforyou.backend.domain.user.UserWeeklyView
import java.time.LocalDate

data class UserViewDetailDto(
    val id: Long?,
    val userId: Long?,
    val count: Int,
    val dateTime: String
) {
    companion object {
        fun from(view: UserDailyView): UserViewDetailDto =
            UserViewDetailDto(
                id = view.id,
                userId = view.user.id,
                count = view.count,
                dateTime = view.targetedAt.toString()
            )

        fun from(view: UserWeeklyView): UserViewDetailDto =
            UserViewDetailDto(
                id = view.id,
                userId = view.user.id,
                count = view.count,
                dateTime = view.targetedAt.toString()
            )
    }
}
