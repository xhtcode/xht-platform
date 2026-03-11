package com.xht.auth.authentication.service.impl;

import com.xht.auth.authentication.service.ITokenService;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.GlobalErrorStatusCode;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.oauth2.token.TokenInfoLightningCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * 令牌服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements ITokenService {

    private final OAuth2AuthorizationService authorizationService;

    private final TokenInfoLightningCache tokenInfoLightningCache;

    /**
     * 检查令牌有效性
     *
     * @param token 令牌
     */
    @Override
    public void checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(GlobalErrorStatusCode.TOKEN_EXPIRED);
        }
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        // 如果令牌不存在，返回 401 错误
        if (Objects.isNull(Optional.ofNullable(authorization).map(OAuth2Authorization::getAccessToken).orElse(null))) {
            throw new BusinessException(GlobalErrorStatusCode.TOKEN_EXPIRED);
        }
    }

    /**
     * 删除令牌
     *
     * @param token 令牌
     */
    @Override
    @SuppressWarnings("all")
    public void removeToken(String token) {
        if (StringUtils.isEmpty(token)) {
            log.warn("token 令牌为空，无法删除!");
            return;
        }
        // 删除 Token 信息缓存
        tokenInfoLightningCache.deleteTokenInfo(token);
        // 根据 Token 查询授权信息
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        // 清空 Access Token
        if (Objects.nonNull(authorization)) {
            authorizationService.remove(authorization);
        }
    }
}
