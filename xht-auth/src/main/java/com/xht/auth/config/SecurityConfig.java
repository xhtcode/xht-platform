package com.xht.auth.config;

import com.xht.auth.constant.CustomAuthorizationGrantType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.UUID;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {


    //@Bean
    //@Order(2)
    //public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
    //        throws Exception {
    //    http
    //            .authorizeHttpRequests((authorize) -> authorize
    //                    .requestMatchers("/login/captcha").permitAll()
    //                    .anyRequest().authenticated()
    //            )
    //            .exceptionHandling(handlingConfigurer -> {
    //                handlingConfigurer.authenticationEntryPoint(new Http401UnauthorizedEntryPoint());//请求未认证的接口
    //                handlingConfigurer.accessDeniedHandler(new Http401AccessDeniedHandler());// 请求未授权的接口
    //            })
    //            .formLogin(Customizer.withDefaults());
    //
    //    return http.build();
    //}

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("oidc-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(CustomAuthorizationGrantType.PASSWORD)
                .redirectUri("http://127.0.0.1:3000/login")
                .postLogoutRedirectUri("http://127.0.0.1:8080/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).requireProofKey(true).build())
                .build();

        return new InMemoryRegisteredClientRepository(oidcClient);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("http://127.0.0.1:3000");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}