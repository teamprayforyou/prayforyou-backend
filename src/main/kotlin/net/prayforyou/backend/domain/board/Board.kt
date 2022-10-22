package net.prayforyou.backend.domain.board

import net.prayforyou.backend.domain.board.enums.BoardType
import net.prayforyou.backend.domain.user.User
import net.prayforyou.backend.global.common.exception.ValidationException
import net.prayforyou.backend.presenter.request.enums.FeedBackActionType
import net.prayforyou.backend.presenter.request.enums.FeedBackType
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

/**
 * 게시판
 * */
@Entity
@Table(name = "board")
@EntityListeners(AuditingEntityListener::class)
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    var type: BoardType,

    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(name = "view")
    var view: Int = 0,

    @Column(name = "good")
    var good: Int = 0,

    @Column(name = "bad")
    var bad: Int = 0,

    @Column(name = "is_deleted")
    var isDeleted: Boolean,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun update(title: String, content: String) {
        this.title = title
        this.content = content
    }

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

    fun delete() {
        this.isDeleted = true
    }

    companion object {
        fun of(content: String, title: String, user: User): Board =
            Board(
                type = BoardType.FREE,
                title = title,
                content = content,
                user = user,
                isDeleted = false
            )
    }
}