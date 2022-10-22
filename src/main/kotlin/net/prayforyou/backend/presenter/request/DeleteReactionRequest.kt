package net.prayforyou.backend.presenter.request

import net.prayforyou.backend.presenter.request.enums.BoardTargetType

data class DeleteReactionRequest(
    val reactionId: Long,
    val userId: Long,
    val type: BoardTargetType
)