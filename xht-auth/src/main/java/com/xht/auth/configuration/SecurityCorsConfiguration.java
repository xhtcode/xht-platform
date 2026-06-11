package com.xht.auth.configuration;

import com.xht.auth.configuration.properties.XhtOauth2Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityCorsConfiguration {

    private final XhtOauth2Properties xhtOauth2Properties;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.setAllowedMethods(List.of(HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name()));
        config.setAllowedOrigins(xhtOauth2Properties.getAllowedOrigins());
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}