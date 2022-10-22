package net.prayforyou.backend.application.board

import net.prayforyou.backend.domain.board.BoardComment
import net.prayforyou.backend.domain.board.BoardReply
import net.prayforyou.backend.global.common.exception.NotFoundDataException
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.board.BoardCommentRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.board.BoardReplyRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.board.BoardRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import net.prayforyou.backend.presenter.request.CreateCommentRequest
import net.prayforyou.backend.presenter.request.CreateReplyRequest
import net.prayforyou.backend.presenter.request.DeleteReactionRequest
import net.prayforyou.backend.presenter.request.UpdateReactionRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BoardCommentService(
    private val boardCommentRepository: BoardCommentRepository,
    private val boardReplyRepository: BoardReplyRepository,
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository
) {
    fun create(request: CreateCommentRequest) {
        val board = boardRepository.findByIdAndIsDeletedFalse(request.boardId)
            ?: throw NotFoundDataException()

        val user = userRepository.findByIdOrNull(request.userId)
            ?: throw NotFoundDataException()

        boardCommentRepository.save(
            BoardComment.of(board, request.content, user)
        )
    }

    fun create(request: CreateReplyRequest) {
        val board = boardRepository.findByIdAndIsDeletedFalse(request.boardId)
            ?: throw NotFoundDataException()

        val user = userRepository.findByIdOrNull(request.userId)
            ?: throw NotFoundDataException()

        val comment = boardCommentRepository.findByIdAndIsDeletedFalse(request.commentId)
            ?: throw NotFoundDataException()

        boardReplyRepository.save(
            BoardReply.of(board, user, comment, request.content)
        )
    }

    fun update(request: UpdateReactionRequest) {
        val user = userRepository.findByIdOrNull(request.userId)
            ?: throw NotFoundDataException()

        if (request.type.isComment()) {
            val comment = boardCommentRepository.findByIdAndIsDeletedFalse(request.reactionId)
                ?: throw NotFoundDataException()
            comment.update(user, request.content)
        } else {
            val reply = boardReplyRepository.findByIdAndIsDeletedFalse(request.reactionId)
                ?: throw NotFoundDataException()
            reply.update(user, request.content)
        }
    }

    fun delete(request: DeleteReactionRequest) {
        val user = userRepository.findByIdOrNull(request.userId)
            ?: throw NotFoundDataException()

        if (request.type.isComment()) {
            val comment = boardCommentRepository.findByIdAndIsDeletedFalse(request.reactionId)
                ?: throw NotFoundDataException()
            comment.delete(user)
        } else {
            val reply = boardReplyRepository.findByIdAndIsDeletedFalse(request.reactionId)
                ?: throw NotFoundDataException()
            reply.delete(user)
        }

    }
}