package net.prayforyou.backend.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")  // 모든 도메인 허용
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메서드 설정
            .allowedHeaders("*")  // 모든 헤더 허용
            .allowCredentials(true)  // 인증 정보를 포함한 요청도 허용
    }
}