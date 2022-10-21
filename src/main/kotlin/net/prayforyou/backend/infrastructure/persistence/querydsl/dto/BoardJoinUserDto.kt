package net.prayforyou.backend.infrastructure.persistence.querydsl.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.querydsl.core.annotations.QueryProjection
import net.prayforyou.backend.domain.board.enums.BoardType
import java.time.LocalDateTime

data class BoardJoinUserDto @QueryProjection constructor(
    val id: Long,
    val userId: Long,
    val bad: Int,
    @JsonProperty("content")
    val content: String,
    @JsonProperty("created_at")
    val createdAt: LocalDateTime,
    val good: Int,
    val isDeleted: Boolean,
    val password: String,
    val title: String,
    val type: BoardType,
    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime,
    val view: Int,
    val email: String,
    val nickName: String,
    val clanName: String? = "무소속"
)