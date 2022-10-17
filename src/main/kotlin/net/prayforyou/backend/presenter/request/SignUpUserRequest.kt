package net.prayforyou.backend.presenter.request

import com.fasterxml.jackson.annotation.JsonProperty

data class SignUpUserRequest(
    @JsonProperty("email")
    val email: String,
    @JsonProperty("password")
    val password: String,
    @JsonProperty("rePassword")
    val rePassword: String,
    @JsonProperty("nickname")
    val nickname: String,
    @JsonProperty("clanId")
    val clanId: String
)