package net.prayforyou.backend.domain.event.model

data class RedTotalResult(
    val assist_cnt: Int?,
    val damage_cnt: String?,
    val death_cnt: Int?,
    val grade: String?,
    val head_cnt: Int?,
    val kill_cnt: Int?,
    val mission_cnt: Int?,
    val save_cnt: Int?
)
