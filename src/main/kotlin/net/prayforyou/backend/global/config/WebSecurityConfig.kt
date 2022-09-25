package net.prayforyou.backend.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


//@Configuration
//@EnableWebSecurity
class WebSecurityConfig() : WebMvcConfigurer {
//
//    companion object {
//        private val PERMIT_URL = arrayOf(
//            "/banner/**",
//            "/search/**",
//            "/view/**"
//        )
//    }
//
//    @Bean
//    fun filterChain(http: HttpSecurity): SecurityFilterChain? =
//        http
//            .csrf().disable()
//            .cors()
//            .and()
//            .authorizeRequests()
//            .antMatchers(*PERMIT_URL)
//            .permitAll()
//            .and()
//            .addFilterBefore(checkUserViewFilter, UsernamePasswordAuthenticationFilter::class.java)
//            .build()
}