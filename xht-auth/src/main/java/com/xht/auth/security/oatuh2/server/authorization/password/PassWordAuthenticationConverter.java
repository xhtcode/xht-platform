package com.xht.auth.security.oatuh2.server.authorization.password;

import com.xht.auth.security.oatuh2.server.authorization.AbstractAuthenticationConverter;
import com.xht.auth.constant.CustomAuthorizationGrantType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Set;

/**
 * 描述 ： 密码模式转换器
 * @author xht
 **/
@Slf4j
public class PassWordAuthenticationConverter extends AbstractAuthenticationConverter {

    /**
     * 子类实现转换.
     *
     * @param authentication       认证参数
     * @param scopes               范围
     * @param additionalParameters 扩展参数
     */
    @Override
    protected Authentication convert(Authentication authentication, Set<String> scopes, Map<String, Object> additionalParameters) {
        return new PassWordAuthenticationToken(authentication, scopes, additionalParameters);
    }

    /**
     * 获取认证类型.
     *
     * @return 认证类型
     */
    @Override
    protected String getGrantType() {
        return CustomAuthorizationGrantType.PASSWORD.getValue();
    }
}
