package net.prayforyou.backend.presenter.response

data class UserRankingResponse(
    val userNexonId: Int,
    val name: String,
    val clanMarkUrl: String,
    val winCount: Int,
    val loseCount: Int,
    val winLosePercent: Double,
    val killDeath: Double,
    val killPerGame: Double,
    val score: Long
)
