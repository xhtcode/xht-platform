package com.xht.framework.security.configurers;

import cn.hutool.core.util.ArrayUtil;
import com.xht.framework.security.properties.PermitAllUrlProperties;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * 自定义授权配置
 *
 * @author xht
 **/
public record CustomAuthorizeHttpRequestsConfigurer(
        PermitAllUrlProperties permitAllUrlProperties) implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorize) {
        authorize
                // 白名单路径全部放行
                .requestMatchers(ArrayUtil.toArray(permitAllUrlProperties.getUrls(), String.class)).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
    }
}
