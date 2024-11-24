package net.prayforyou.backend.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy


@Configuration
@EnableWebSecurity
class SecurityConfig(
) : WebSecurityConfigurerAdapter() {

    companion object {
        val PERMIT_URL = arrayOf(
            "/",
            "/admin/**",
            "/banner/**",
            "/battle/**",
            "/clan/**",
            "/season/**",
            "/user/**",
            // TODO 글쓰기는 막을 것
            "/board/**"
        )
    }

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(*(PERMIT_URL))
            .permitAll()
            .anyRequest().authenticated()
    }
}
