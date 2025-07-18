package com.xht.auth.authorization.password;

import com.xht.auth.authorization.AbstractAuthenticationConverter;
import com.xht.framework.oauth2.constant.CustomAuthorizationGrantType;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Set;

/**
 * @author xht
 **/
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
