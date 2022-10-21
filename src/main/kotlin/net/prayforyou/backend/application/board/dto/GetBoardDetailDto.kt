package net.prayforyou.backend.application.board.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import net.prayforyou.backend.domain.board.Board
import net.prayforyou.backend.domain.board.BoardComment
import net.prayforyou.backend.domain.board.BoardReply
import net.prayforyou.backend.global.util.DateUtil

data class GetBoardDetailDto(
    val id: Long,
    val title: String,
    val content: String,
    val view: Int,
    val good: Int,
    val bad: Int,
    @JsonProperty("isDeleted")
    val isDeleted: Boolean,
    val createdAt: String,
    val userId: Long,
    val nickName: String,
    val clanId: Long?,
    val clanNickName: String?,
    val clanMarkUrl: String?,
    val comment: List<GetBoardCommentDto>
) {
    companion object {
        fun of(board: Board, comment: List<BoardComment>, reply: List<BoardReply>): GetBoardDetailDto =
            GetBoardDetailDto(
                id = board.id!!,
                title = board.title,
                content = board.content,
                view = board.view,
                good = board.good,
                bad = board.bad,
                isDeleted = board.isDeleted,
                createdAt = DateUtil.calculateTimeByBoard(
                    board.createdAt
                ),
                userId = board.user.id!!,
                nickName = board.user.nickname!!,
                clanId = board.user.clanId?.id,
                clanNickName = board.user.clanId?.clanNickname,
                clanMarkUrl = board.user.clanId?.clanMarkUrl,
                comment = comment.map { GetBoardCommentDto.of(it, reply) }
            )
    }
}