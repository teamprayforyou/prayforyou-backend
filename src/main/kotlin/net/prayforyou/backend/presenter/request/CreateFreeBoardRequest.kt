package net.prayforyou.backend.presenter.request

data class CreateFreeBoardRequest(
    val userId: Long,
    val title: String,
    val content: String
)