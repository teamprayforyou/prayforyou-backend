package net.prayforyou.backend.infrastructure.crawler.webclient.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class BattleLogResponseDto(
    val battleLog: List<BattleLog>?
)

data class BattleLog(
    @JsonProperty("death_x")
    val deathX: Double?,
    @JsonProperty("death_y")
    val deathY: Double?,
    @JsonProperty("event_category")
    val eventCategory: String?,
    val event_icon: String?,
    val event_key: Int?,
    val event_text: String?,
    val event_time: String?,
    @JsonProperty("event_type")
    val eventType: String?,
    @JsonProperty("kill_x")
    val killX: Double?,
    @JsonProperty("kill_y")
    val killY: Double?,
    val map: Any?,
    val mask_nick: String?,
    val mask_target_nick: Any?,
    val round: String?,
    val target_user_nexon_sn: Int?,
    val target_user_nick: String?,
    @JsonProperty("target_event_type")
    val targetEventType: String,
    val user_nexon_sn: Int?,
    @JsonProperty("target_weapon")
    val targetWeapon: String,
    val user_nick: String?,
    @JsonProperty("weapon")
    val weapon: String?
)
