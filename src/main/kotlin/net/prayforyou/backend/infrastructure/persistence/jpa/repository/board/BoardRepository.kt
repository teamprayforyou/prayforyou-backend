package net.prayforyou.backend.infrastructure.persistence.jpa.repository.board

import net.prayforyou.backend.domain.board.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : JpaRepository<Board, Long> {
}