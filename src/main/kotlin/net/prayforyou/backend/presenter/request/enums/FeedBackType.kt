package net.prayforyou.backend.presenter.request.enums

enum class FeedBackType {
    GOOD, BAD;

    fun isBad(): Boolean = this == BAD

    fun isGood(): Boolean = this == GOOD
}