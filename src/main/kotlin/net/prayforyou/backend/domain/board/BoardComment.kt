package net.prayforyou.backend.domain.board

import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.exception.ValidationException
import net.prayforyou.backend.presenter.request.enums.FeedBackActionType
import net.prayforyou.backend.presenter.request.enums.FeedBackType
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

/**
 * 댓글
 * */
@Entity
@Table(name = "board_comment")
@EntityListeners(AuditingEntityListener::class)
class BoardComment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    var board: Board,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(name = "content")
    var content: String,

    @Column(name = "good")
    var good: Int = 0,

    @Column(name = "bad")
    var bad: Int = 0,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun of(board: Board, content: String, user: User): BoardComment =
            BoardComment(
                board = board,
                content = content,
                user = user
            )
    }


    // TODO 추상클래스로 good이랑, view 뺴기 후에 리펙토링
    fun updateFeedBack(actionType: FeedBackActionType, feedBackType: FeedBackType, user: User) {
        if (this.user != user) {
            throw ValidationException()
        }

        if (actionType.isCancel() && feedBackType.isBad()) {
            this.bad -= 1
        } else if (actionType.isCancel() && feedBackType.isGood()) {
            this.good -= 1
        } else if (actionType.isInsert() && feedBackType.isBad()) {
            this.bad += 1
        } else if (actionType.isInsert() && feedBackType.isGood()) {
            this.good += 1
        }
    }

    fun delete(user: User) {
        if (this.user.id != user.id) {
            throw ValidationException()
        }

        this.isDeleted = true
    }

    fun update(user: User, content: String) {
        if (this.user.id != user.id) {
            throw ValidationException()
        }

        this.content = content
    }
}