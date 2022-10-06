package net.prayforyou.backend.domain.event.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class MatchJson(
    val blueTotalResult: List<BlueTotalResult>,
    val matchResultDataInfo: MatchResultDataInfo,
    val myMatchRating: MyMatchRating,
    val parseData: ParseData,
    val blueUserList: List<BlueUser?>?,
    val redTotalResult: List<RedTotalResult>,
    val redUserList: List<RedUser>,
    val survivalTotalResult: List<Any>,
    val totalResult: TotalResult,
    val userbattleInfo: List<UserbattleInfo>
)
