package net.prayforyou.backend.presenter.request

import com.fasterxml.jackson.annotation.JsonProperty

data class SignUpUserRequest(
    val email: String,
    val password: String,
    val rePassword: String,
    val nickname: String,
    val clanId: String
)