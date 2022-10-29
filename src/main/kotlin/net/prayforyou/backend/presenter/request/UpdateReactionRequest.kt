package net.prayforyou.backend.presenter.request

import com.fasterxml.jackson.annotation.JsonProperty
import net.prayforyou.backend.presenter.request.enums.BoardTargetType

data class UpdateReactionRequest(
    @JsonProperty("userId")
    val userId: Long,
    @JsonProperty("reactionId")
    val reactionId: Long,
    @JsonProperty("content")
    val content: String,
    @JsonProperty("boardId")
    val boardId: Long,
    @JsonProperty("type")
    val type: BoardTargetType
)