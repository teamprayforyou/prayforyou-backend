package net.prayforyou.backend.application.user


data class UserViewDto(
    val dailyView: List<UserViewDetailDto>,
    val weeklyView: List<UserViewDetailDto>
)