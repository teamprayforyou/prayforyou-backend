package net.prayforyou.backend.presenter.request

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateFreeBoardRequest(
    @JsonProperty("content")
    val content: String,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("boardId")
    val boardId: Long,
    @JsonProperty("userId")
    val userId: Long
)