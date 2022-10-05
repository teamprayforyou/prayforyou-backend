package net.prayforyou.backend.domain.user

import net.prayforyou.backend.domain.clan.Clan
import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "user")
@Where(clause = "is_deleted = 'false'")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "nickname")
    var nickname: String?,

    @Column(name = "user_nexon_id")
    var userNexonId: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clan_id")
    var clanId: Clan?,

    var score: Long? = 0,

    var killDeath: Double? = 0.0,

    var killCount: Int? = 0,

    var deathCount: Int? = 0,

    var gameCount: Int? = 0,

    var killPerGame: Double? = 0.0,

    var primaryUseGun: String? = null,

    var winLoosePercent: Double? = 0.0,

    var winCount: Int? = 0,

    var sniperCount: Int? = 0,

    var ripleCount: Int? = 0,

    val isDeleted: Boolean? = false
) {

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

    fun calculateScore() {
        this.score = (this.killCount!! * 5 - this.deathCount!! * 3 + this.winCount!! * 10 - this.gameCount!! - winCount!! * 5).toLong()
    }

    fun updateKillDeath(killCount: Int, deathCount: Int) {
        this.killDeath =
            this.killCount!! + killCount * 100 / (this.killCount!! + killCount + this.deathCount!! + deathCount).toDouble()
    }

    fun increaseKillCount(killCount: Int) {
        this.killCount = this.killCount!! + killCount
    }

    fun increaseDeathCount(deathCount: Int) {
        this.deathCount = this.deathCount!! + deathCount
    }

    fun updateKillPerGame() {
        this.killPerGame = (this.killCount!! / this.gameCount!!).toDouble()
    }

    fun increaseGameCount() {
        this.gameCount = this.gameCount!! + 1
    }

    fun updateWinLosePercent() {
        this.winLoosePercent = (this.winCount!! * 100) / this.gameCount!!.toDouble()
    }

    fun increaseWinCount() {
        this.winCount = this.winCount!! + 1
    }

    fun setPrimaryUseGun(ripleCount: Int, sniperCount: Int) {
        this.sniperCount = this.sniperCount!! + sniperCount
        this.ripleCount = this.ripleCount!! + ripleCount

        if (this.sniperCount!! >= this.ripleCount!!) {
            this.primaryUseGun = "스나이퍼"
        } else {
            this.primaryUseGun = "라이플"
        }
    }

    companion object {
        fun from(
            userNick: String? = null,
            userNexonId: Int,
            clanId: Clan?,
            score: Long,
            killDeath: Double,
            killPerGame: Double,
            primaryUseGun: String,
            winLoosePercent: Double,
        ): User =
            User(
                nickname = userNick,
                userNexonId = userNexonId,
                clanId = clanId,
                score = score,
                killDeath = killDeath,
                killPerGame = killPerGame,
                primaryUseGun = primaryUseGun,
                winLoosePercent = winLoosePercent,
                killCount = 0,
                deathCount = 0,
                gameCount = 0
            )

        fun initialUser(
            clan: Clan,
            userNexonId: Int,
            userNickname: String
        ): User =
            User(clanId = clan, userNexonId = userNexonId, nickname = userNickname)
    }
}
