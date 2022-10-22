package net.prayforyou.backend.presenter.request

data class UpdateFreeBoardRequest(
    val content: String,
    val title: String,
    val boardId: Long,
    val userId: Long
)