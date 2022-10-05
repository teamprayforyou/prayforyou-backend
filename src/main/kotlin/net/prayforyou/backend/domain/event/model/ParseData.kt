package net.prayforyou.backend.domain.event.model

data class ParseData(
    val m_CLAN_enemy_clan_no: String?,
    val m_CLAN_friendly_clan_no: String?,
    val m_CLAN_match_key: Long?,
    val m_CLAN_match_type: String?,
    val matchData: MatchData?
)
