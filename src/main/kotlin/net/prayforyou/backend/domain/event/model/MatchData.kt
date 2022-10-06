package net.prayforyou.backend.domain.event.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class MatchData(
    var M_CLAN_assist_sum_cnt: String?,
    var M_CLAN_clan_exp: String?,
    var M_CLAN_damage_sum_cnt: String?,
    var M_CLAN_death_sum_cnt: String?,
    var M_CLAN_enemy_win_cnt: Int?,
    var M_CLAN_friendly_win_cnt: Int?,
    var M_CLAN_head_sum_cnt: String?,
    var M_CLAN_kill_sum_cnt: String?,
    var M_CLAN_klimit: Int?,
    var M_CLAN_map_no: Int?,
    var M_CLAN_match_time: String?,
    var M_CLAN_mission_sum_cnt: String?,
    var M_CLAN_plimit: Int?,
    var M_CLAN_result_win_clan_no: String?,
    var M_CLAN_round_result: String?,
    var M_CLAN_save_sum_cnt: String?
)
