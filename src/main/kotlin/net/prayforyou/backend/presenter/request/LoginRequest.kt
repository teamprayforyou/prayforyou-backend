package net.prayforyou.backend.presenter.request

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest(
    @JsonProperty("email")
    val email: String,
    @JsonProperty("password")
    val password: String
)