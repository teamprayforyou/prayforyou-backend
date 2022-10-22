package net.prayforyou.backend.presenter.request

import net.prayforyou.backend.presenter.request.enums.FeedBackActionType
import net.prayforyou.backend.presenter.request.enums.BoardTargetType
import net.prayforyou.backend.presenter.request.enums.FeedBackType

data class UpdateFeedBackRequest(
    val feedBackId: Long,
    val feedBackType: FeedBackType,
    val targetType: BoardTargetType,
    val actionType: FeedBackActionType,
    val userId: Long
)