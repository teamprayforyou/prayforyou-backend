package net.prayforyou.backend.domain.board

import net.prayforyou.backend.domain.board.enums.BoardType
import java.time.LocalDateTime
import javax.persistence.*

/**
 * 댓글
 * */
@Entity
@Table(name = "board_comment")
class BoardComment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    var board: Board,

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

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)