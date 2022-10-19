package net.prayforyou.backend.application.board

import net.prayforyou.backend.application.board.dto.GetAllPageBoardDto
import net.prayforyou.backend.application.board.dto.GetBoardDetailDto
import net.prayforyou.backend.global.common.exception.NotFoundDataException
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.board.BoardCommentRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.board.BoardReplyRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.board.BoardRepository
import net.prayforyou.backend.infrastructure.persistence.querydsl.BoardQueryDslRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BoardService(
    private val boardQueryDslRepository: BoardQueryDslRepository,
    private val boardRepository: BoardRepository,
    private val boardCommentRepository: BoardCommentRepository,
    private val boardReplyRepository: BoardReplyRepository

) {
    fun get(pageable: Pageable): Page<GetAllPageBoardDto> {
        val boardJoinUserDto = boardQueryDslRepository.findAllByPage(pageable)
        return boardJoinUserDto.map { GetAllPageBoardDto.convert(it) }
    }

    fun getById(boardId: Long): GetBoardDetailDto {
        val board = boardRepository.findByIdOrNull(boardId) ?: throw NotFoundDataException("존재하지 않는 게시글이에요 😭")
        val comment = boardCommentRepository.findAllByBoardId(board.id!!)
        val reply = boardReplyRepository.findAllByBoardId(board.id!!)
        return GetBoardDetailDto.of(board, comment, reply)
    }
}