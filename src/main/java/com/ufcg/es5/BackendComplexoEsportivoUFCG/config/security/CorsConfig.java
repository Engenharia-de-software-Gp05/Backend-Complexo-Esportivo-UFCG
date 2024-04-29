package com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://fronted-complexo-esportivo-ufcg.vercel.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "CONNECT");
    }
}