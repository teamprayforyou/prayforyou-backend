package net.prayforyou.backend.global.common.exception

data class NotFoundDataException(
    override val code: String = "NOT_FOUND_DATA_EXCEPTION",
    override val message: String = "데이터를 찾을 수 없습니다."
) : RuntimeException(), CommonException