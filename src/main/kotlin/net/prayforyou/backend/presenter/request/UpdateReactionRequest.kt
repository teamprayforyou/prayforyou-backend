package net.prayforyou.backend.presenter.request

import net.prayforyou.backend.presenter.request.enums.BoardTargetType

data class UpdateReactionRequest(
    val userId: Long,
    val reactionId: Long,
    val content: String,
    val boardId: Long,
    val type: BoardTargetType
)