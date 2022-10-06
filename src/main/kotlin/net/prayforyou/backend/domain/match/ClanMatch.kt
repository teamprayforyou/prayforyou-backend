package net.prayforyou.backend.domain.match

import net.prayforyou.backend.domain.clan.Clan
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
class ClanMatch (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var matchId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "red_clan_id")
    var redClan: Clan,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blue_clan_id")
    var blueClan: Clan,

    var isRedTeamWin: Boolean,

    var plusScore: Int,

    var matchTimeKorean: String,

    var totalMatchTime: String,

    var matchStartTime: String,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
)
