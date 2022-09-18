package net.prayforyou.backend.global.common.exception

import net.prayforyou.backend.global.common.CommonException

data class ValidationException(
    override val code: String = "VALIDATION_EXCEPTION",
    override val message: String = "유효하지 않은 데이터입니다."
) : RuntimeException(), CommonException