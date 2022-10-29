package net.prayforyou.backend.presenter.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateReplyRequest(
    @JsonProperty("boardId")
    val boardId: Long,
    @JsonProperty("userId")
    val userId: Long,
    @JsonProperty("commentId")
    val commentId: Long,
    @JsonProperty("content")
    val content: String
)