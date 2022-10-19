package net.prayforyou.backend.application.board.dto

import com.fasterxml.jackson.annotation.JsonProperty
import net.prayforyou.backend.domain.board.BoardReply


data class GetBoardReplyDto(
    val id: Long?,
    val content: String,
    val view: Int,
    val good: Int,
    val bad: Int,
    @JsonProperty("isDeleted")
    val isDeleted: Boolean,
    val createdAt: String,
    val updatedAt: String
) {
    companion object {
        fun from(boardReply: BoardReply): GetBoardReplyDto =
            GetBoardReplyDto(
                id = boardReply.id,
                content = boardReply.content,
                view = boardReply.view,
                good = boardReply.good,
                bad = boardReply.bad,
                isDeleted = boardReply.isDeleted,
                createdAt = boardReply.createdAt.toString(),
                updatedAt = boardReply.updatedAt.toString()
            )
    }
}