package net.prayforyou.backend.domain.event.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class MatchResultDataInfo(
    val blue_clan_img1: String?,
    val blue_clan_img2: String?,
    val blue_clan_name: String?,
    val blue_clan_no: String?,
    val blue_result: String?,
    val blue_win_cnt: Int?,
    val is_clan: Boolean?,
    val lose_team_name: String?,
    val match_time: String?,
    val match_type: String?,
    val my_team: String?,
    val red_clan_img1: String?,
    val red_clan_img2: String?,
    val red_clan_name: String?,
    val red_clan_no: String?,
    val red_result: String?,
    val red_win_cnt: Int?,
    val round_result: List<String>?,
    val win_clan_no: String?,
    val win_team_name: String?
)
