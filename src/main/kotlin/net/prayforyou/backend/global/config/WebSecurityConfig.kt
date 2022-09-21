package net.prayforyou.backend.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import net.prayforyou.backend.global.filter.CheckUserViewFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val checkUserViewFilter: CheckUserViewFilter
) : WebMvcConfigurer {

    companion object {
        private val PERMIT_URL = arrayOf(
            "/banner/**",
            "/search/**",
            "/view/**"
        )
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? =
        http
            .csrf().disable()
            .cors()
            .and()
            .authorizeRequests()
            .antMatchers(*PERMIT_URL)
            .permitAll()
            .and()
            .addFilterBefore(checkUserViewFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
}