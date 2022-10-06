package net.prayforyou.backend.domain.user.model

class UserJsonModel(
    val message: String?,
    val result: Int?,
    val resultClanInfo: Any?,
    val resultClanUserList: List<ResultClanUser>?,
    val rtnCode: Int?
)
