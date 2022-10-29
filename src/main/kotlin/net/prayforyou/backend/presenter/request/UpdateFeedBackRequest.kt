package net.prayforyou.backend.presenter.request

import com.fasterxml.jackson.annotation.JsonProperty
import net.prayforyou.backend.presenter.request.enums.FeedBackActionType
import net.prayforyou.backend.presenter.request.enums.BoardTargetType
import net.prayforyou.backend.presenter.request.enums.FeedBackType

data class UpdateFeedBackRequest(
    @JsonProperty("feedBackId")
    val feedBackId: Long,
    @JsonProperty("feedBackType")
    val feedBackType: FeedBackType,
    @JsonProperty("targetType")
    val targetType: BoardTargetType,
    @JsonProperty("actionType")
    val actionType: FeedBackActionType,
    @JsonProperty("userId")
    val userId: Long
)