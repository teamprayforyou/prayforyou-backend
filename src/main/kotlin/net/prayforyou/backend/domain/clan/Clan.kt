package net.prayforyou.backend.domain.clan

import net.prayforyou.backend.domain.clan.enums.ClanLevel
import org.hibernate.annotations.Where
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "clan")
@Entity
@EntityListeners(AuditingEntityListener::class)
class Clan (
    @Id
    @GeneratedValue
    var id: Long? = null,

    var clanId: String,

    var clanName: String,

    var clanNickname: String,

    var winCount: Int = 0,

    var loseCount: Int = 0,

    var isDownDanger: Boolean,

    @Enumerated(EnumType.STRING)
    var clanLevel: ClanLevel,

    var score: Long,

    var winLosePercent: Double,

    var openKakaoLink: String,

    var clanMarkUrl: String,

    var isDeleted: Boolean,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),
) {
    fun updateWinLoseCount(winCount: Int, loseCount: Int) {
        this.winCount = this.winCount + winCount
        this.loseCount = this.loseCount + loseCount
    }

    fun calculateWinLosePercent() {
        if (winCount > 0 || loseCount > 0) {
            this.winLosePercent = (this.winCount * 100) / (this.winCount + this.loseCount).toDouble()
        }
    }

    fun calculateScore() {
        this.score = (this.winCount * 10 + this.loseCount * -10).toLong()
    }

}
