package net.prayforyou.backend.presenter.request

data class SignUpUserRequest(
    val email: String,
    val password: String,
    val rePassword: String,
    val nickname: String,
    val clanId: Long
)