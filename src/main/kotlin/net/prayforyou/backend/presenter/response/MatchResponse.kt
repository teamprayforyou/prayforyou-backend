package net.prayforyou.backend.presenter.response

data class MatchResponse(
    val matchId: Long,
    val gameProgressTime: String,
    val isWin: Boolean,
    val lastGameDay: String,
    val addScore: Int,
    val redTeam: RedTeam,
    val blueTeam: BlueTeam,
    val totalPages: Int,
    val isLast: Boolean
)

data class RedTeam (
    val clanId: Long,
    val ladderPoint: Int,
    val members: List<User>,
    val clanLevel: String,
    val clanName: String,
)

data class BlueTeam(
    val clanId: Long,
    val ladderPoint: Int,
    val members: List<User>,
    val clanLevel: String,
    val clanName: String,
)

data class User(
    val name: String,
    val id: Long
)