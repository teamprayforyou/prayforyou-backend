package net.prayforyou.backend.domain.match

import net.prayforyou.backend.domain.clan.Clan
import net.prayforyou.backend.domain.user.User
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class MatchUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clan_match_id")
    var clanMatch: ClanMatch,

    var isSniper: Boolean,

    var killCount: Int,

    var deathCount: Int,

    var killDeathPercent: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "play_clan_id")
    var playClan: Clan,

    var plusScore: Int,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
)
