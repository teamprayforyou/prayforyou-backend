package net.prayforyou.backend.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import net.prayforyou.backend.global.common.CommonResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationExceptionHandler : AuthenticationEntryPoint, AccessDeniedHandler {

    companion object {
        val objectMapper = ObjectMapper()
    }

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        setError("인증이 필요합니다.", HttpServletResponse.SC_UNAUTHORIZED, response)
    }

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        setError("권한이 없습니다.", HttpServletResponse.SC_FORBIDDEN, response)
    }

    private fun setError(message: String, status: Int, response: HttpServletResponse) {
        val json = objectMapper.writeValueAsString(CommonResponse.convert(message))
        response.setHeader("Content-Type", "application/json;charset=UTF-8")
        response.status = status
        response.writer.write(json)
        response.writer.flush()
    }
}