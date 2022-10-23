package net.prayforyou.backend.presenter.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateFreeBoardRequest(
    @JsonProperty("userId")
    val userId: Long,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("content")
    val content: String
)