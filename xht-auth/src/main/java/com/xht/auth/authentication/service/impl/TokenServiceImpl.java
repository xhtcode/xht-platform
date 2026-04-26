package com.xht.auth.authentication.service.impl;

import com.xht.auth.authentication.dao.IAuthenticationDao;
import com.xht.auth.authentication.domain.response.TokenUserInfoResponse;
import com.xht.auth.authentication.service.ITokenService;
import com.xht.auth.constant.AuthorizationConstant;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.GlobalErrorStatusCode;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.framework.oauth2.token.TokenInfoLightningCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

    private final IAuthenticationDao authenticationDao;

    private final RedisRepository redisRepository;

    /**
     * 获取令牌用户信息
     *
     * @param userId 用户id
     * @return 令牌用户信息
     */
    @Override
    public TokenUserInfoResponse getTokenUserInfo(Long userId) {
        String userInfoKey = Keys.createKeyTemplate(AuthorizationConstant.TOKEN_USER_INFO_KEY, userId);
        Long expire = redisRepository.getExpire(userInfoKey);
        // 判断缓存里面是否有，没有就查询
        if (expire < 5) {
            TokenUserInfoResponse tokenUserInfoResponse = authenticationDao.findByUserId(userId);
            redisRepository.set(userInfoKey, tokenUserInfoResponse, Keys.randomExpire(30, 50), TimeUnit.MINUTES);
            return tokenUserInfoResponse;
        }
        return redisRepository.get(userInfoKey);
    }


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
    public void removeToken(String token) {
        if (StringUtils.isEmpty(token)) {
            log.warn("token 令牌为空，无法删除!");
            return;
        }
        // 删除 Token 信息缓存
        tokenInfoLightningCache.deleteTokenInfo(token);
        // 根据 Token 查询授权信息
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        // 处理自定义退出事件，保存相关日志
        SpringContextUtils.publishEvent(new LogoutSuccessEvent(new PreAuthenticatedAuthenticationToken(
                "admin","1234456")));
        // 清空 Access Token
        if (Objects.nonNull(authorization)) {
            // 处理自定义退出事件，保存相关日志
            SpringContextUtils.publishEvent(new LogoutSuccessEvent(new PreAuthenticatedAuthenticationToken(
                    authorization.getPrincipalName(), authorization.getRegisteredClientId())));
            authorizationService.remove(authorization);
        }
    }

}
