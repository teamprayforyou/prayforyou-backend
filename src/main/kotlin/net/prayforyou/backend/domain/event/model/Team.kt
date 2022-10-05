package net.prayforyou.backend.domain.event.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Team(
    val clan_name: Any?,
    val clan_no: String?,
    val team_name: Any?,
    val team_no: String?
)
