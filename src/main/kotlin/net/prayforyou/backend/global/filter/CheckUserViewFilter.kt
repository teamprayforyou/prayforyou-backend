package net.prayforyou.backend.global.filter

import net.prayforyou.backend.application.user.UserViewService
import net.prayforyou.backend.infrastructure.persistence.jpa.provider.user.UserProvider
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CheckUserViewFilter(
    private val userViewService: UserViewService
) : OncePerRequestFilter() {


    // TODO IP 중복 체크 고민 ... DB 저장은 안하고 싶음..
    // FIXME 진심 너무별로 프론트 하나의 API콜 하도록 수정한다음 호출 할때 마다 조회수 업데이트
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.requestURI.endsWith("/place")) {
            userViewService.updateView(
                request.requestURI.split("/")[2].toLong()
            )
        }

        filterChain.doFilter(request, response)
    }
}