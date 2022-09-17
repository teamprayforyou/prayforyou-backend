package net.prayforyou.backend.global.common

import net.prayforyou.backend.global.common.exception.NotFoundDataException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionAdviceHandler {

    @ExceptionHandler(NotFoundDataException::class)
    fun notFoundException(exception: CommonException): ResponseEntity<CommonResponse<Nothing>> =
        ResponseEntity(CommonResponse.error(exception.message), HttpStatus.NOT_FOUND)
}