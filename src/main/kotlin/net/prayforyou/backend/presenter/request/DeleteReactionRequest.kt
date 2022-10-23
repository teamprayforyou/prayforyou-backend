package net.prayforyou.backend.presenter.request

import com.fasterxml.jackson.annotation.JsonProperty
import net.prayforyou.backend.presenter.request.enums.BoardTargetType

data class DeleteReactionRequest(
    @JsonProperty("reactionId")
    val reactionId: Long,
    @JsonProperty("userId")
    val userId: Long,
    @JsonProperty("type")
    val type: BoardTargetType
)