package com.indent.proptech.proptech;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(private val env: Environment) : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {

        val origins: Array<String> = env.getProperty("cors.allowed-origins", Array<String>::class.java) ?: arrayOf()

        registry.addMapping("/**")
            .allowedOrigins(*origins)
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600)
    }
}
