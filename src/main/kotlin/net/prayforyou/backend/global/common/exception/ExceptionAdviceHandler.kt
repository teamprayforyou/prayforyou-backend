package net.prayforyou.backend.global.common.exception

import net.prayforyou.backend.global.common.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionAdviceHandler {

    @ExceptionHandler(NotFoundDataException::class)
    fun notFoundException(exception: CommonException): ResponseEntity<CommonResponse<Nothing>> =
        ResponseEntity(CommonResponse.error(exception.message), HttpStatus.NOT_FOUND)

    @ExceptionHandler(ValidationException::class)
    fun validationException(exception: CommonException): ResponseEntity<CommonResponse<Nothing>> =
        ResponseEntity(CommonResponse.error(exception.message), HttpStatus.BAD_REQUEST)
}