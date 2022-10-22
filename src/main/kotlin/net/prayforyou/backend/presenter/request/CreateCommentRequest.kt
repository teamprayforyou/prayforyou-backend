package net.prayforyou.backend.presenter.request

data class CreateCommentRequest(
    val boardId: Long,
    val userId: Long,
    val content: String
)