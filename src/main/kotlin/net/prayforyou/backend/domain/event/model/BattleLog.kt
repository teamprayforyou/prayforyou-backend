package net.prayforyou.backend.domain.event.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class BattleLog(
    val death_x: Double?,
    val death_y: Double?,
    val event_category: String?,
    val event_icon: String?,
    val event_key: Int?,
    val event_text: String?,
    val event_time: String?,
    val event_type: String?,
    val kill_x: Double?,
    val kill_y: Double?,
    val map: Any?,
    val mask_nick: String?,
    val mask_target_nick: String?,
    val myself_tag: Boolean?,
    val round: String?,
    val target_event_category: String?,
    val target_event_type: String?,
    val target_team_name: String?,
    val target_team_no: String?,
    val target_user_nexon_sn: Int?,
    val target_user_nick: String?,
    val target_weapon: String?,
    val team_name: String?,
    val team_no: String?,
    val user_nexon_sn: Int?,
    val user_nick: String?,
    val weapon: String?,
    val win_flag: String?,
    val win_team_name: Any?,
    val win_team_no: String?
) {
    fun isKill(): Boolean = event_type == "kill"
}
