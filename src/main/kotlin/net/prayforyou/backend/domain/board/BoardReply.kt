package net.prayforyou.backend.domain.board

import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

/**
 * 대댓글
 * */
@Entity
@Table(name = "board_reply")
@EntityListeners(AuditingEntityListener::class)
class BoardReply(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_comment_id")
    var boardComment: BoardComment,

    @Column(name = "author")
    var author: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "view")
    var view: Int,

    @Column(name = "good")
    var good: Int,

    @Column(name = "bad")
    var bad: Int,

    @Column(name = "is_deleted")
    var isDeleted: Boolean,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
