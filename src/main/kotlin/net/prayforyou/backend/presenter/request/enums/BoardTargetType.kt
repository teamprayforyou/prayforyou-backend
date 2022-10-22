package net.prayforyou.backend.presenter.request.enums

enum class BoardTargetType {
    BOARD, COMMENT, REPLY;

    fun isComment(): Boolean = this == COMMENT

    fun isReply(): Boolean = this == REPLY
}