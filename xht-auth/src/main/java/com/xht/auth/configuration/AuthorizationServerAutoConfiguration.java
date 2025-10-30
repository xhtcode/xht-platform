package com.xht.auth.configuration;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.xht.auth.authorization.password.PassWordAuthenticationConverter;
import com.xht.auth.authorization.password.PassWordAuthenticationProvider;
import com.xht.auth.authorization.token.TokenAuthenticationFailureHandler;
import com.xht.auth.authorization.token.TokenAuthenticationSuccessHandler;
import com.xht.auth.authorization.token.TokenRevocationAuthenticationFailureHandler;
import com.xht.auth.authorization.token.TokenRevocationAuthenticationSuccessHandler;
import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

/**
 * @author xht
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class AuthorizationServerAutoConfiguration {

    private final BasicUserDetailsService basicUserDetailsService;

    private final ICaptchaService iCaptchaService;


    @Bean
    @Order(1)
    // @formatter:off
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
                                                                      OAuth2AuthorizationService authorizationService,
                                                                      OAuth2TokenGenerator<?> tokenGenerator

    ) throws Exception {
        PassWordAuthenticationProvider passWordAuthenticationProvider = new PassWordAuthenticationProvider(authorizationService, tokenGenerator, basicUserDetailsService, iCaptchaService);
        PassWordAuthenticationConverter passWordAuthenticationConverter = new PassWordAuthenticationConverter();
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
        http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .oidc(Customizer.withDefaults())  // Enable OpenID Connect 1.0
                                .tokenEndpoint(tokenEndpoint -> {
                                    tokenEndpoint.accessTokenResponseHandler(new TokenAuthenticationSuccessHandler());
                                    tokenEndpoint.errorResponseHandler(new TokenAuthenticationFailureHandler());
                                    tokenEndpoint.authenticationProviders(providers -> {
                                        providers.add(passWordAuthenticationProvider);
                                    });
                                    tokenEndpoint.accessTokenRequestConverters((converters) -> {
                                        converters.add(passWordAuthenticationConverter);
                                    });
                                })
                                .tokenRevocationEndpoint(tokenEndpoint ->{
                                    tokenEndpoint.revocationResponseHandler(new TokenRevocationAuthenticationSuccessHandler());
                                    tokenEndpoint.errorResponseHandler(new TokenRevocationAuthenticationFailureHandler());
                                })
                )
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .exceptionHandling(
                        (exceptions) ->
                        {
                            LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint("/login");
                            MediaTypeRequestMatcher mediaTypeRequestMatcher = new MediaTypeRequestMatcher(MediaType.TEXT_HTML);
                            exceptions.defaultAuthenticationEntryPointFor(loginUrlAuthenticationEntryPoint, mediaTypeRequestMatcher);
                        }
                );
        return http.build();
    }
    // @formatter:on

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public OAuth2TokenGenerator<?> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }
}
