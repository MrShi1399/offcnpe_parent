package com.offcn.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 配置过滤器，解决跨域问题
 *
 * @author 13320
 */
@Configuration
public class CorsConfig {

    private CorsConfiguration buildConfig(){
        CorsConfiguration configuration=new CorsConfiguration();
        //允许任何域名访问
        configuration.addAllowedOrigin("*");
        //允许任何头
        configuration.addAllowedHeader("*");
        //允许任何方法（get，post等）
        configuration.addAllowedMethod("*");
        return configuration;
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",buildConfig());
        return new CorsFilter(source);
    }
}
