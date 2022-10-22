package net.prayforyou.backend.presenter.request.enums

enum class FeedBackActionType {
    INSERT, CANCEL;

    fun isCancel(): Boolean =
        this == CANCEL

    fun isInsert(): Boolean =
        this == INSERT
}
