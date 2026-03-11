package com.xht.auth.authentication.service;

/**
 * 令牌服务接口
 *
 * @author xht
 **/
public interface ITokenService {

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
