package com.xht.auth.authorization.password;

import com.xht.auth.authorization.AbstractAuthenticationToken;
import com.xht.auth.constant.CustomAuthorizationGrantType;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Set;

/**
 * 自定义的密码授权类型
 *
 * @author xht
 **/
public class PassWordAuthenticationToken extends AbstractAuthenticationToken {


    /**
     * 构造函数，用于创建PassWordAuthenticationToken对象
     *
     * @param clientPrincipal      客户端主体
     * @param scopes               授权范围
     * @param additionalParameters 额外的参数，可以为空
     */
    public PassWordAuthenticationToken(Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(CustomAuthorizationGrantType.PASSWORD, clientPrincipal, scopes, additionalParameters);
    }
}
