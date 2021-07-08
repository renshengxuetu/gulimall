package com.rs.gulimall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 配置允许跨域请求
 */
@Configuration
public class GulimallCorsConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 配置跨域
        corsConfiguration.addAllowedHeader("*");    // 允许哪些头
        corsConfiguration.addAllowedMethod("*");    // 允许哪些方式
        corsConfiguration.addAllowedOrigin("*");    // 允许那些来源
        corsConfiguration.setAllowCredentials(true);    // 是否允许携带 Cookie

        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }

}
