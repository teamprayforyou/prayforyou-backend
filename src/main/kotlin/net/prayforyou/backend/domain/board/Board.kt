package net.prayforyou.backend.domain.board

import net.prayforyou.backend.domain.board.enums.BoardType
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
    var type: BoardType,

    @Column(name = "title")
    var title: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "author")
    var author: String,

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