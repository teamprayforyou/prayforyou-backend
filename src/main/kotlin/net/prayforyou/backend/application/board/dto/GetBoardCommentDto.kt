package net.prayforyou.backend.application.board.dto

import com.fasterxml.jackson.annotation.JsonProperty
import net.prayforyou.backend.domain.board.BoardComment
import net.prayforyou.backend.domain.board.BoardReply
import net.prayforyou.backend.global.util.DateUtil

data class GetBoardCommentDto(
    val id: Long?,
    val content: String,
    val good: Int,
    val boardId: Long,
    val userId: Long,
    val userNickName: String,
    val clanNickName: String? = "무소속",
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
                good = comment.good,
                boardId = comment.board.id!!,
                userId = comment.user.id!!,
                userNickName = comment.user.nickname!!,
                clanNickName = comment.user.clanId?.clanNickname,
                isDeleted = comment.isDeleted,
                createdAt = DateUtil.calculateTimeByBoard(comment.createdAt),
                updatedAt = comment.updatedAt.toString(),
                reply = reply.map { GetBoardReplyDto.from(it) }
            )
    }
}