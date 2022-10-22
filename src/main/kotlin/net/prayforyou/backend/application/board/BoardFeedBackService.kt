package net.prayforyou.backend.application.board

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.exception.NotFoundDataException
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.board.BoardCommentRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.board.BoardReplyRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.board.BoardRepository
import net.prayforyou.backend.infrastructure.persistence.jpa.repository.user.UserRepository
import net.prayforyou.backend.presenter.request.UpdateFeedBackRequest
import net.prayforyou.backend.presenter.request.enums.BoardTargetType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BoardFeedBackService(
    private val userRepository: UserRepository,
    private val boardRepository: BoardRepository,
    private val boardCommentRepository: BoardCommentRepository,
    private val boardReplyRepository: BoardReplyRepository
) {
    fun update(request: UpdateFeedBackRequest) {
        val user = userRepository.findByIdOrNull(request.userId) ?: throw NotFoundDataException()

        when (request.targetType) {
            BoardTargetType.BOARD -> updateBoard(request, user)
            BoardTargetType.COMMENT -> updateComment(request, user)
            BoardTargetType.REPLY -> updateReply(request, user)
        }
    }

    private fun updateReply(request: UpdateFeedBackRequest, user: User) {
        val reply = boardReplyRepository.findByIdAndIsDeletedFalse(request.feedBackId)
            ?: throw NotFoundDataException()
        reply.updateFeedBack(request.actionType, request.feedBackType, user)
    }

    private fun updateComment(request: UpdateFeedBackRequest, user: User) {
        val comment = boardCommentRepository.findByIdAndIsDeletedFalse(request.feedBackId)
            ?: throw NotFoundDataException()
        comment.updateFeedBack(request.actionType, request.feedBackType, user)

    }

    private fun updateBoard(request: UpdateFeedBackRequest, user: User) {
        val board = boardRepository.findByIdAndIsDeletedFalse(request.feedBackId)
            ?: throw NotFoundDataException()
        board.updateFeedBack(request.actionType, request.feedBackType, user)
    }
}