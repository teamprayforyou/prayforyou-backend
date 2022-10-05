package net.prayforyou.backend.presenter.response

data class MatchDetail (
    var gameStartTime: String,
    var isRedTeamWin: Boolean,
    var redUsers: List<DetailUser>,
    var blueUsers: List<DetailUser>
)

data class DetailUser (
    val userNexonId: Long,
    val name: String,
    val ladderPoint: Int,
    val killCount: Int,
    val deathCount: Int,
    val isSniper: Boolean
)
