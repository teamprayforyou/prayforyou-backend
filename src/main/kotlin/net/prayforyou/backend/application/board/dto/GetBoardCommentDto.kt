package net.prayforyou.backend.application.board.dto

import com.fasterxml.jackson.annotation.JsonProperty
import net.prayforyou.backend.domain.board.BoardComment
import net.prayforyou.backend.domain.board.BoardReply

data class GetBoardCommentDto(
    val id: Long?,
    val content: String,
    val view: Int,
    val good: Int,
    @JsonProperty("isDeleted")
    val isDeleted: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val reply: List<GetBoardReplyDto>
) {
    companion object {
        fun of(comment: BoardComment, reply: List<BoardReply>): GetBoardCommentDto =
            GetBoardCommentDto(
                id = comment.id,
                content = comment.content,
                view = comment.view,
                good = comment.good,
                isDeleted = comment.isDeleted,
                createdAt = comment.createdAt.toString(),
                updatedAt = comment.updatedAt.toString(),
                reply = reply.map { GetBoardReplyDto.from(it) }
            )
    }
}