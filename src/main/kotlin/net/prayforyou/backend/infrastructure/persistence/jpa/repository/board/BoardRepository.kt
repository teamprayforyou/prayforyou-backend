package net.prayforyou.backend.infrastructure.persistence.jpa.repository.board

import net.prayforyou.backend.domain.board.Board
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : JpaRepository<Board, Long> {
    fun findAllByIsDeletedFalse(pageable: Pageable): List<Board>
    fun findByIdAndIsDeletedFalse(id: Long): Board?
}