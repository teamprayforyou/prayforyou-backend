package net.prayforyou.backend.domain.event.model

data class MatchData(
    val m_CLAN_assist_sum_cnt: String?,
    val m_CLAN_clan_exp: String?,
    val m_CLAN_damage_sum_cnt: String?,
    val m_CLAN_death_sum_cnt: String?,
    val m_CLAN_enemy_win_cnt: Int?,
    val m_CLAN_friendly_win_cnt: Int?,
    val m_CLAN_head_sum_cnt: String?,
    val m_CLAN_kill_sum_cnt: String?,
    val m_CLAN_klimit: Int?,
    val m_CLAN_map_no: Int?,
    val m_CLAN_match_time: String?,
    val m_CLAN_mission_sum_cnt: String?,
    val m_CLAN_plimit: Int?,
    val m_CLAN_result_win_clan_no: String?,
    val m_CLAN_round_result: String?,
    val m_CLAN_save_sum_cnt: String?
)
