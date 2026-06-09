package com.xht.auth.security.oauth2;

import com.xht.auth.configuration.properties.XhtOauth2Properties;
import com.xht.auth.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCancelApproveAuthenticationProvider;
import com.xht.auth.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoMapper;
import com.xht.auth.security.oauth2.server.authorization.web.AuthorizationEndpointFailureHandler;
import com.xht.auth.security.oauth2.server.authorization.web.AuthorizationEndpointSuccessHandler;
import com.xht.auth.security.web.authentication.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.*;
import org.springframework.util.Assert;

/**
 * 描述：OAuth2 授权服务器自定义器
 *
 * @author xht
 **/
@Slf4j
@Setter
public class OAuth2AuthorizationServerCustomizer implements Customizer<OAuth2AuthorizationServerConfigurer> {

    private OAuth2AuthorizationService authorizationService;

    private XhtOauth2Properties.AuthorizationServer authorizationServerProperties;

    @Override
    public void customize(OAuth2AuthorizationServerConfigurer authorizationServer) {
        Assert.notNull(authorizationService, "authorizationService must not be null");
        Assert.notNull(authorizationServerProperties, "authorizationServerProperties must not be null");
        authorizationServer.clientAuthentication(clientAuthenticationCustomizer());
        // Enable OpenID Connect 1.0
        authorizationServer.oidc(oidc());
        authorizationServer.authorizationEndpoint(authorizationEndpoint());
        // 令牌端点
        authorizationServer.tokenEndpoint(tokenEndpoint());
        // 令牌注销端点
        authorizationServer.tokenRevocationEndpoint(tokenRevocationEndpoint());
    }

    /**
     * 客户端认证自定义器
     * <p>配置客户端认证失败时的错误响应处理器</p>
     *
     * @return 客户端认证配置定制器
     */
    private Customizer<OAuth2ClientAuthenticationConfigurer> clientAuthenticationCustomizer() {
        return configurer -> configurer.errorResponseHandler(new OAuth2ClientAuthenticationFailureHandler());
    }

    /**
     * OpenID Connect 1.0 配置自定义器
     * <p>配置用户信息端点的自定义映射器，用于定制 OIDC 用户信息的返回内容</p>
     *
     * @return OIDC 配置定制器
     */
    private Customizer<OidcConfigurer> oidc() {
        return oidc -> oidc.userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userInfoMapper(new OidcUserInfoMapper()));
    }

    /**
     * 授权端点配置自定义器
     * <p>配置授权端点的以下行为：</p>
     * <ul>
     *     <li>自定义同意页面地址</li>
     *     <li>授权成功响应处理器</li>
     *     <li>授权失败响应处理器</li>
     *     <li>注册授权取消/审批的自定义认证提供者</li>
     * </ul>
     *
     * @return 授权端点配置定制器
     */
    private Customizer<OAuth2AuthorizationEndpointConfigurer> authorizationEndpoint() {
        return authorizationEndpoint -> {
            authorizationEndpoint.consentPage(authorizationServerProperties.getConsentPage());
            authorizationEndpoint.authorizationResponseHandler(new AuthorizationEndpointSuccessHandler());
            authorizationEndpoint.errorResponseHandler(new AuthorizationEndpointFailureHandler());
            authorizationEndpoint.authenticationProvider(new OAuth2AuthorizationCancelApproveAuthenticationProvider(authorizationService));
        };
    }

    /**
     * 令牌端点配置自定义器
     * <p>配置令牌端点的成功和失败响应处理器</p>
     *
     * @return 令牌端点配置定制器
     */
    private Customizer<OAuth2TokenEndpointConfigurer> tokenEndpoint() {
        return tokenEndpoint -> {
            tokenEndpoint.accessTokenResponseHandler(new TokenEndpointSuccessHandler());
            tokenEndpoint.errorResponseHandler(new TokenEndpointFailureHandler());
        };
    }

    /**
     * 令牌注销端点配置自定义器
     * <p>配置令牌注销端点的成功和失败响应处理器</p>
     *
     * @return 令牌注销端点配置定制器
     */
    private Customizer<OAuth2TokenRevocationEndpointConfigurer> tokenRevocationEndpoint() {
        return tokenRevocationEndpoint -> {
            tokenRevocationEndpoint.revocationResponseHandler(new TokenRevocationAuthenticationSuccessHandler());
            tokenRevocationEndpoint.errorResponseHandler(new TokenRevocationAuthenticationFailureHandler());
        };
    }

}
