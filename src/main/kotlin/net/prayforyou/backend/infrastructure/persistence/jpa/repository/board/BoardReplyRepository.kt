package net.prayforyou.backend.infrastructure.persistence.jpa.repository.board

import net.prayforyou.backend.domain.board.BoardReply
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardReplyRepository : JpaRepository<BoardReply, Long> {
}