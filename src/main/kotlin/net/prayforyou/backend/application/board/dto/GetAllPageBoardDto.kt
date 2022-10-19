package net.prayforyou.backend.application.board.dto

import com.fasterxml.jackson.annotation.JsonProperty
import net.prayforyou.backend.domain.board.enums.BoardType
import net.prayforyou.backend.infrastructure.persistence.querydsl.dto.BoardJoinUserDto
import java.time.LocalDateTime

data class GetAllPageBoardDto(
    val id: Long,
    val userId: Long,
    val bad: Int,
    val content: String,
    val createdAt: String,
    val good: Int,
    @JsonProperty("isDeleted")
    val isDeleted: Boolean,
    val password: String,
    val title: String,
    val type: BoardType,
    val updatedAt: String,
    val view: Int,
    val email: String,
    val nickName: String
) {
    companion object {
        fun convert(dto: BoardJoinUserDto): GetAllPageBoardDto =
            GetAllPageBoardDto(
                id = dto.id,
                userId = dto.userId,
                bad = dto.bad,
                content = dto.content,
                createdAt = dto.createdAt.toString(),
                good = dto.good,
                isDeleted = dto.isDeleted,
                password = dto.password,
                title = dto.title,
                type = dto.type,
                updatedAt = dto.updatedAt.toString(),
                view = dto.view,
                email = dto.email,
                nickName = dto.nickName
            )
    }
}