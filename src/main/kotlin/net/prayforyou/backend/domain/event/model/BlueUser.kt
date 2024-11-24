package net.prayforyou.backend.domain.event.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class BlueUser(
    val assist: Int?,
    val damage: String?,
    val death: Int?,
    val grade: String?,
    val grademark: String?,
    val headshot: Int?,
    val kill: Int?,
    val mask_nick: String?,
    val mission: Int?,
    val nickname: String?,
    val oid: Int?,
    val oneself: Boolean?,
    val percentile: Double?,
    val save: Int?
)
