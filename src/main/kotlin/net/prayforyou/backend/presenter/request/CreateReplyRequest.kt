package net.prayforyou.backend.presenter.request

data class CreateReplyRequest(
    val boardId: Long,
    val userId: Long,
    val commentId: Long,
    val content: String
)