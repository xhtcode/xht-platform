package com.xht.auth.security.oauth2.server.authorization;

import com.xht.auth.constant.AuthorizationConstant;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.crypto.MD5Utils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 认证信息
 *
 * @author xht
 **/
@Slf4j
@Service
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {

    @Resource
    private RedisRepository redisRepository;

    /**
     * 保存 {@link OAuth2Authorization}。
     *
     * @param authorization 要保存的 {@link OAuth2Authorization}
     */
    @Override
    public void save(OAuth2Authorization authorization) {
        remove(authorization);
        OAuth2AuthorizationWrapper authorizationWrapper = OAuth2AuthorizationWrapper.wrap(authorization);
        String principalName = authorization.getPrincipalName();
        AuthorizationGrantType authorizationGrantType = authorization.getAuthorizationGrantType();
        if (AuthorizationGrantType.CLIENT_CREDENTIALS.equals(authorizationGrantType)){
            principalName = authorization.getId();
        }
        String idKey = Keys.createKey(AuthorizationConstant.AUTHORIZATION_ID_KEY_PREFIX, principalName);
        authorizationWrapper
                .accessToken((cacheInfo) -> save(cacheInfo.getKey(), authorization, cacheInfo.getTimeout(), cacheInfo.getTimeUnit()))
                .refreshToken((cacheInfo) -> save(cacheInfo.getKey(), authorization, cacheInfo.getTimeout(), cacheInfo.getTimeUnit()))
                .authorizationCode((cacheInfo) -> save(cacheInfo.getKey(), authorization, cacheInfo.getTimeout(), cacheInfo.getTimeUnit()))
                .state((cacheInfo) -> save(cacheInfo.getKey(), authorization, cacheInfo.getTimeout(), cacheInfo.getTimeUnit()))
                .oidcIdToken((cacheInfo) -> save(cacheInfo.getKey(), authorization, cacheInfo.getTimeout(), cacheInfo.getTimeUnit()))
                .userCode((cacheInfo) -> save(cacheInfo.getKey(), authorization, cacheInfo.getTimeout(), cacheInfo.getTimeUnit()))
                .deviceCode((cacheInfo) -> save(cacheInfo.getKey(), authorization, cacheInfo.getTimeout(), cacheInfo.getTimeUnit()))
                .maxTimeout((timeOut, timeUnit) -> save(idKey, authorization, timeOut, timeUnit));
    }

    /**
     * 保存 {@link OAuth2Authorization}。
     *
     * @param key                 缓存key
     * @param oAuth2Authorization {@link OAuth2Authorization}
     * @param timeout             超时时间
     * @param timeUnit            时间单位
     */
    private void save(String key, OAuth2Authorization oAuth2Authorization, long timeout, TimeUnit timeUnit) {
        redisRepository.set(key, oAuth2Authorization, timeout, timeUnit);
    }


    /**
     * 删除 {@link OAuth2Authorization}。
     *
     * @param authorization 要删除的 {@link OAuth2Authorization}
     */
    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        log.debug("从缓存中删除授权信息，id:{}", authorization.getId());
        String idKey = Keys.createKey(AuthorizationConstant.AUTHORIZATION_ID_KEY_PREFIX, authorization.getId());
        OAuth2Authorization dbOAuth2Authorization = redisRepository.get(idKey);
        if (dbOAuth2Authorization == null) {
            return;
        }
        OAuth2AuthorizationWrapper authorizationWrapper = OAuth2AuthorizationWrapper.wrap(dbOAuth2Authorization);
        authorizationWrapper
                .accessToken((cacheInfo) -> redisRepository.delete(cacheInfo.getKey()))
                .refreshToken((cacheInfo) -> redisRepository.delete(cacheInfo.getKey()))
                .authorizationCode((cacheInfo) -> redisRepository.delete(cacheInfo.getKey()))
                .state((cacheInfo) -> redisRepository.delete(cacheInfo.getKey()))
                .oidcIdToken((cacheInfo) -> redisRepository.delete(cacheInfo.getKey()))
                .userCode((cacheInfo) -> redisRepository.delete(cacheInfo.getKey()))
                .deviceCode((cacheInfo) -> redisRepository.delete(cacheInfo.getKey()))
                .maxTimeout((timeOut, timeUnit) -> redisRepository.delete(idKey));
    }

    /**
     * 根据提供的 {@code id} 查找并返回对应的 {@link OAuth2Authorization}，
     * 如果未找到则返回 {@code null}。
     *
     * @param id 授权标识符
     * @return 如果找到了匹配的 {@link OAuth2Authorization} 则返回它，否则返回 {@code null}
     */
    @Override
    public OAuth2Authorization findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        log.debug("从缓存中获取授权信息，id:{}", id);
        String idKey = Keys.createKey(AuthorizationConstant.AUTHORIZATION_ID_KEY_PREFIX, id);
        return redisRepository.get(idKey);
    }

    /**
     * 返回包含指定 {@code token} 的 {@link OAuth2Authorization}，
     * 如果未找到则返回 {@code null}。
     *
     * @param token     token凭证
     * @param tokenType {@link OAuth2TokenType token类型}
     * @return 如果找到了匹配的 {@link OAuth2Authorization} 则返回它，否则返回 {@code null}
     */
    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        String tokenTypeValue = Optional.ofNullable(tokenType).map(OAuth2TokenType::getValue).orElse(null);
        log.debug("从缓存中获取授权信息，tokenType:{} token:{}", tokenTypeValue, token);
        String signature = MD5Utils.generateSignature(token);
        if (StringUtils.hasText(tokenTypeValue)) {
            return findByToken(signature, tokenTypeValue).orElse(null);
        }
        return findByToken(signature, AuthorizationConstant.ACCESS_TOKEN)
                .or(() -> findByToken(signature, AuthorizationConstant.REFRESH_TOKEN))
                .or(() -> findByToken(signature, AuthorizationConstant.CODE))
                .or(() -> findByToken(signature, AuthorizationConstant.STATE))
                .or(() -> findByToken(signature, AuthorizationConstant.ID_TOKEN))
                .or(() -> findByToken(signature, AuthorizationConstant.USER_CODE))
                .or(() -> findByToken(signature, AuthorizationConstant.DEVICE_CODE))
                .orElse(null);
    }

    /**
     * 根据提供的 {@code token} 查找并返回对应的 {@link OAuth2Authorization}，
     * 如果未找到则返回 {@code null}。
     *
     * @param signature token凭证签名
     * @param tokenType {@link OAuth2TokenType token类型}
     * @return 如果找到了匹配的 {@link OAuth2Authorization} 则返回它，否则返回 {@code null}
     */
    public Optional<OAuth2Authorization> findByToken(String signature, String tokenType) {
        OAuth2Authorization oAuth2Authorization =
                redisRepository.get(Keys.createKey(AuthorizationConstant.AUTHORIZATION_TOKEN_KEY_PREFIX, tokenType, signature));
        return Optional.ofNullable(oAuth2Authorization);
    }

}
