package net.prayforyou.backend.application.user.dto


data class UserViewDto(
    val dailyView: List<UserViewDetailDto>,
    val weeklyView: List<UserViewDetailDto>
)