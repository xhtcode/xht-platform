package com.xht.auth.security.oatuh2.server.authorization;

import com.xht.auth.redis.converter.Oauth2AuthorizationConverter;
import com.xht.auth.redis.entity.Oauth2AuthorizationEntity;
import com.xht.auth.redis.repository.Oauth2AuthorizationRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * 认证信息
 *
 * @author xht
 **/
@Slf4j
@Service
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final Oauth2AuthorizationConverter authorizationConverter = new Oauth2AuthorizationConverter();
    @Resource
    private Oauth2AuthorizationRepository authorizationRepository;

    /**
     * 保存 {@link OAuth2Authorization}。
     *
     * @param authorization 要保存的 {@link OAuth2Authorization}
     */
    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        Oauth2AuthorizationEntity entity = authorizationConverter.convert(authorization);
        authorizationRepository.save(entity);
    }


    /**
     * 删除 {@link OAuth2Authorization}。
     *
     * @param authorization 要删除的 {@link OAuth2Authorization}
     */
    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        authorizationRepository.deleteById(authorization.getId());
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
        return authorizationRepository.findById(id).map(authorizationConverter::reverse).orElse(null);
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
        Optional<Oauth2AuthorizationEntity> result;
        if (tokenType == null) {
            result = authorizationRepository.findByState(token)
                    .or(() -> authorizationRepository.findByAuthorizationCodeValue(token))
                    .or(() -> authorizationRepository.findByAccessTokenValue(token))
                    .or(() -> authorizationRepository.findByOidcIdTokenValue(token))
                    .or(() -> authorizationRepository.findByRefreshTokenValue(token))
                    .or(() -> authorizationRepository.findByUserCodeValue(token))
                    .or(() -> authorizationRepository.findByDeviceCodeValue(token));
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            result = authorizationRepository.findByAuthorizationCodeValue(token);
        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            result = authorizationRepository.findByAccessTokenValue(token);
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
            result = authorizationRepository.findByRefreshTokenValue(token);
        } else if (OidcParameterNames.ID_TOKEN.equals(tokenType.getValue())) {
            result = authorizationRepository.findByOidcIdTokenValue(token);
        } else if (OAuth2ParameterNames.USER_CODE.equals(tokenType.getValue())) {
            result = authorizationRepository.findByUserCodeValue(token);
        } else if (OAuth2ParameterNames.DEVICE_CODE.equals(tokenType.getValue())) {
            result = authorizationRepository.findByDeviceCodeValue(token);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            result = authorizationRepository.findByState(token);
        } else {
            result = Optional.empty();
        }
        return result.map(authorizationConverter::reverse).orElse(null);
    }

}
