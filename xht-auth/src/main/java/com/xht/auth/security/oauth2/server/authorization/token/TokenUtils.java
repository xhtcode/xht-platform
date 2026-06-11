package com.xht.auth.security.oauth2.server.authorization.token;

import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.utils.IdUtils;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

import static com.xht.auth.constant.AuthorizationConstant.XHT_TOKEN_PREFIX;

/**
 * 描述：token 工具类
 *
 * @author xht
 **/
public final class TokenUtils {

    private TokenUtils() {
        throw new UnsupportedOperationException();
    }


    public static String generateToken(OAuth2TokenType tokenType) {
        return XHT_TOKEN_PREFIX + tokenType.getValue() + StringConstant.POINT + IdUtils.simpleUUID();
    }


}
