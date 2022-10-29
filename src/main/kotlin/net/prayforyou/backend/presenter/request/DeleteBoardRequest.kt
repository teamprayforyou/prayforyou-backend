package net.prayforyou.backend.presenter.request

import com.fasterxml.jackson.annotation.JsonProperty

data class DeleteBoardRequest(
    @JsonProperty("userId")
    val userId: Long
)