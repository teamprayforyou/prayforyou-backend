package net.prayforyou.backend.presenter.response

import net.prayforyou.backend.application.user.dto.UserViewDetailDto

data class UserViewResponse(
    val dailyView: List<UserViewDetailDto>,
    val weeklyView: List<UserViewDetailDto>
)