package net.prayforyou.backend.global.filter

import net.prayforyou.backend.global.config.SecurityConfig
import net.prayforyou.backend.global.util.JwtTokenUtil
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
    private val jwtTokenUtil: JwtTokenUtil
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        logger.info("[request] ${request.method} ${request.requestURI}")

        val isPermit =
            SecurityConfig.PERMIT_URL.any { it.replace("/**", "") in request.requestURI }

        if (isPermit) {
            filterChain.doFilter(request, response)
            return
        }

        val token = jwtTokenUtil.getTokenFromHeader(request)

        // 토큰이 유효한지 검증
        if (!jwtTokenUtil.validToken(token)) {
            return
        }

        filterChain.doFilter(request, response)
    }

}