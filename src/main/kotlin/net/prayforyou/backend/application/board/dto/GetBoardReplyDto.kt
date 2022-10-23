package net.prayforyou.backend.application.board.dto

import com.fasterxml.jackson.annotation.JsonProperty
import net.prayforyou.backend.domain.board.BoardReply
import net.prayforyou.backend.global.util.DateUtil


data class GetBoardReplyDto(
    val id: Long?,
    val content: String,
    val userId: Long,
    val userNickName: String,
    val clanNickName: String? = "무소속",
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
                userNickName = boardReply.user.nickname!!,
                clanNickName = boardReply.user.clanId?.clanNickname,
                good = boardReply.good,
                userId = boardReply.user.id!!,
                bad = boardReply.bad,
                isDeleted = boardReply.isDeleted,
                createdAt = DateUtil.calculateTimeByBoard(boardReply.createdAt),
                updatedAt = boardReply.updatedAt.toString()
            )
    }
}