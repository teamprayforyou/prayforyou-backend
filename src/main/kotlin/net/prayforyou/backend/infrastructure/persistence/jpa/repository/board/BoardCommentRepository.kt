package net.prayforyou.backend.infrastructure.persistence.jpa.repository.board

import net.prayforyou.backend.domain.board.BoardComment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardCommentRepository : JpaRepository<BoardComment, Long> {
    fun findAllByBoardId(id: Long): List<BoardComment>
}