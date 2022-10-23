package net.prayforyou.backend.application.board

import net.prayforyou.backend.application.board.dto.GetAllPageBoardDto
import net.prayforyou.backend.application.board.dto.GetBoardDetailDto
import net.prayforyou.backend.domain.board.Board
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.exception.NotFoundDataException
import net.prayforyou.backend.global.common.exception.ValidationException
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.board.BoardCommentRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.board.BoardReplyRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.board.BoardRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import net.prayforyou.backend.infrastructure.persistence.querydsl.BoardQueryDslRepository
import net.prayforyou.backend.presenter.request.CreateFreeBoardRequest
import net.prayforyou.backend.presenter.request.DeleteBoardRequest
import net.prayforyou.backend.presenter.request.UpdateFreeBoardRequest
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
    private val boardReplyRepository: BoardReplyRepository,
    private val userRepository: UserRepository
) {
    fun get(pageable: Pageable): Page<GetAllPageBoardDto> {
        val boardJoinUserDto = boardQueryDslRepository.findAllByPage(pageable)
        return boardJoinUserDto.map { GetAllPageBoardDto.convert(it) }
    }

    fun getById(boardId: Long): GetBoardDetailDto {
        val board = boardRepository.findByIdOrNull(boardId) ?: throw NotFoundDataException("존재하지 않는 게시글이에요 😭")
        val comment = boardCommentRepository.findAllByBoardId(board.id!!)
        val reply = boardReplyRepository.findAllByBoardId(board.id!!)
            .groupBy { it.boardComment.id!! }
        return GetBoardDetailDto.of(board, comment, reply)
    }

    fun create(
        request: CreateFreeBoardRequest
    ) {
        val user = userRepository.findByIdOrNull(request.userId)
            ?: throw NotFoundDataException("존재하지 않는 유저아이디 입니다")
        boardRepository.save(Board.of(request.content, request.title, user))
    }

    fun update(request: UpdateFreeBoardRequest) {
        val user = userRepository.findByIdOrNull(request.userId)
            ?: throw NotFoundDataException()

        val board = boardRepository.findByIdOrNull(request.boardId)
            ?: throw NotFoundDataException("존재하지 않는 글 입니다")

        validWriteUser(board, user)

        board.update(request.title, request.content)
    }

    private fun validWriteUser(board: Board, user: User) {
        if (user.id != board.user.id) {
            throw ValidationException("유저가 등록한 글이 아닙니다")
        }
    }

    fun delete(boardId: Long, request: DeleteBoardRequest) {
        val user = userRepository.findByIdOrNull(request.userId)
            ?: throw NotFoundDataException()

        val board = boardRepository.findByIdAndIsDeletedFalse(boardId)
            ?: throw NotFoundDataException()

        validWriteUser(board, user)

        board.delete()
    }

    fun updateView(boardId: Long) {
        val board = boardRepository.findByIdOrNull(boardId)
            ?: throw NotFoundDataException()
        board.updateView()
    }
}