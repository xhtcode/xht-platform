package com.xht.auth.authentication.service;

import com.xht.auth.authentication.domain.response.TokenUserInfoResponse;
import com.xht.framework.oauth2.token.response.Oauth2TokenResponse;

/**
 * 令牌服务接口
 *
 * @author xht
 **/
public interface ITokenService {

    /**
     * 获取令牌用户信息
     *
     * @param userId 用户id
     * @return 令牌用户信息
     */
    TokenUserInfoResponse getTokenUserInfo(Long userId);

    /**
     * 检查令牌有效性
     *
     * @param token 令牌
     */
    void checkToken(String token);

    /**
     * 删除令牌
     *
     * @param token 令牌
     */
    void removeToken(String token);

}
