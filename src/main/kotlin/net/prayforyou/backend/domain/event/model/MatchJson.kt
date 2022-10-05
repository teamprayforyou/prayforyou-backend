package net.prayforyou.backend.domain.event.model

class MatchJson(
    val blueTotalResult: List<BlueTotalResult>?,
    val blueUserList: List<Any>?,
    val matchResultDataInfo: MatchResultDataInfo?,
    val myMatchRating: MyMatchRating?,
    val parseData: ParseData?,
    val redTotalResult: List<RedTotalResult>?,
    val redUserList: List<RedUser>?,
    val survivalTotalResult: List<Any>?,
    val totalResult: TotalResult?,
    val userbattleInfo: List<UserbattleInfo>?
)
