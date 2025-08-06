package com.xht.boot.oauth2.service;

import com.xht.boot.oauth2.entity.RedisOAuth2Authorization;
import com.xht.boot.oauth2.entity.token.BasicToken;
import com.xht.boot.oauth2.helper.TokenHelper;
import com.xht.boot.oauth2.repository.RedisOAuth2AuthorizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * 基于redis的授权管理服务
 *
 * @author xht
 */
@Service
@RequiredArgsConstructor
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final RegisteredClientRepository registeredClientRepository;

    private final RedisOAuth2AuthorizationRepository oAuth2AuthorizationRepository;

    @Override
    public void save(OAuth2Authorization authorization) {
        Optional<RedisOAuth2Authorization> existingAuthorization = oAuth2AuthorizationRepository.findById(authorization.getId());

        // 如果已存在则删除后再保存
        existingAuthorization.map(RedisOAuth2Authorization::getId)
                .ifPresent(oAuth2AuthorizationRepository::deleteById);

        // 过期时间，默认永不过期
        long maxTimeout = -1L;
        // 所有code的过期时间，方便计算最大值
        List<Instant> expiresAtList = new ArrayList<>();
        RedisOAuth2Authorization entity = toEntity(authorization);
        // 如果有过期时间就存入
        Optional.ofNullable(entity.getAuthorizationCode())
                .map(BasicToken::getExpiresAt)
                .ifPresent(expiresAtList::add);
        // 如果有过期时间就存入
        Optional.ofNullable(entity.getAccessToken())
                .map(BasicToken::getExpiresAt)
                .ifPresent(expiresAtList::add);

        // 如果有过期时间就存入
        Optional.ofNullable(entity.getRefreshToken())
                .map(BasicToken::getExpiresAt)
                .ifPresent(expiresAtList::add);

        // 如果有过期时间就存入
        Optional.ofNullable(entity.getOidcToken())
                .map(BasicToken::getExpiresAt)
                .ifPresent(expiresAtList::add);

        // 如果有过期时间就存入
        Optional.ofNullable(entity.getUserCode())
                .map(BasicToken::getExpiresAt)
                .ifPresent(expiresAtList::add);

        // 如果有过期时间就存入
        Optional.ofNullable(entity.getDeviceCode())
                .map(BasicToken::getExpiresAt)
                .ifPresent(expiresAtList::add);

        // 获取最大的日期
        Optional<Instant> maxInstant = expiresAtList.stream().max(Comparator.comparing(Instant::getEpochSecond));
        if (maxInstant.isPresent()) {
            // 计算时间差
            Duration between = Duration.between(Instant.now(), maxInstant.get());
            // 转为分钟
            maxTimeout = between.toMinutes();
        }

        // 设置过期时间
        entity.setTimeout(maxTimeout);

        // 保存至redis
        oAuth2AuthorizationRepository.save(entity);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        oAuth2AuthorizationRepository.deleteById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return oAuth2AuthorizationRepository.findById(id)
                .map(this::toObject).orElse(null);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");

        Optional<RedisOAuth2Authorization> result;
        if (tokenType == null) {
            result = oAuth2AuthorizationRepository.findByState(token)
                    .or(() -> oAuth2AuthorizationRepository.findByAuthorizationCodeValue(token))
                    .or(() -> oAuth2AuthorizationRepository.findByAccessTokenValue(token))
                    .or(() -> oAuth2AuthorizationRepository.findByOidcIdTokenValue(token))
                    .or(() -> oAuth2AuthorizationRepository.findByRefreshTokenValue(token))
                    .or(() -> oAuth2AuthorizationRepository.findByUserCodeValue(token))
                    .or(() -> oAuth2AuthorizationRepository.findByDeviceCodeValue(token));
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            result = oAuth2AuthorizationRepository.findByState(token);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            result = oAuth2AuthorizationRepository.findByAuthorizationCodeValue(token);
        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            result = oAuth2AuthorizationRepository.findByAccessTokenValue(token);
        } else if (OidcParameterNames.ID_TOKEN.equals(tokenType.getValue())) {
            result = oAuth2AuthorizationRepository.findByOidcIdTokenValue(token);
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
            result = oAuth2AuthorizationRepository.findByRefreshTokenValue(token);
        } else if (OAuth2ParameterNames.USER_CODE.equals(tokenType.getValue())) {
            result = oAuth2AuthorizationRepository.findByUserCodeValue(token);
        } else if (OAuth2ParameterNames.DEVICE_CODE.equals(tokenType.getValue())) {
            result = oAuth2AuthorizationRepository.findByDeviceCodeValue(token);
        } else {
            result = Optional.empty();
        }
        return result.map(this::toObject).orElse(null);
    }

    /**
     * 将redis中存储的类型转为框架所需的类型
     *
     * @param entity redis中存储的类型
     * @return 框架所需的类型
     */
    private OAuth2Authorization toObject(RedisOAuth2Authorization entity) {
        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(entity.getRegisteredClientId());
        if (registeredClient == null) {
            throw new DataRetrievalFailureException(
                    "The RegisteredClient with id '" + entity.getRegisteredClientId() + "' was not found in the RegisteredClientRepository.");
        }
        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .id(entity.getId())
                .principalName(entity.getPrincipalName())
                .authorizationGrantType(resolveAuthorizationGrantType(entity.getAuthorizationGrantType()))
                .authorizedScopes(StringUtils.commaDelimitedListToSet(entity.getAuthorizedScopes()))
                .attributes(attributes -> attributes.putAll(entity.getAttributes().claims()));
        if (entity.getState() != null) {
            builder.attribute(OAuth2ParameterNames.STATE, entity.getState());
        }
        BasicToken authorizationCode = entity.getAuthorizationCode();
        if (Objects.nonNull(authorizationCode)) {
            builder.token(
                    new OAuth2AuthorizationCode(authorizationCode.getValue(), authorizationCode.getIssuedAt(), authorizationCode.getExpiresAt()),
                    TokenHelper.claimsHolderConsumer(authorizationCode.getMetadata())
            );
        }
        BasicToken accessToken = entity.getAccessToken();
        if (Objects.nonNull(accessToken)) {
            builder.token(
                    new OAuth2AccessToken(accessToken.getType(), accessToken.getValue(), accessToken.getIssuedAt(), accessToken.getExpiresAt(), accessToken.getScopes()),
                    TokenHelper.claimsHolderConsumer(accessToken.getMetadata())
            );
        }
        BasicToken refreshToken = entity.getRefreshToken();
        if (Objects.nonNull(refreshToken)) {
            builder.token(
                    new OAuth2RefreshToken(refreshToken.getValue(), refreshToken.getIssuedAt(), refreshToken.getExpiresAt()),
                    TokenHelper.claimsHolderConsumer(refreshToken.getMetadata())
            );
        }
        BasicToken oidcToken = entity.getOidcToken();
        if (Objects.nonNull(refreshToken)) {
            builder.token(
                    new OidcIdToken(oidcToken.getValue(), oidcToken.getIssuedAt(), oidcToken.getExpiresAt(), TokenHelper.claimsHolderToMap(oidcToken.getClaims())),
                    TokenHelper.claimsHolderConsumer(oidcToken.getMetadata())
            );
        }
        BasicToken userCode = entity.getUserCode();
        if (Objects.nonNull(userCode)) {
            builder.token(
                    new OAuth2UserCode(userCode.getValue(), userCode.getIssuedAt(), userCode.getExpiresAt()),
                    TokenHelper.claimsHolderConsumer(userCode.getMetadata())
            );
        }
        BasicToken deviceCode = entity.getDeviceCode();
        if (Objects.nonNull(deviceCode)) {
            builder.token(
                    new OAuth2DeviceCode(deviceCode.getValue(), deviceCode.getIssuedAt(), deviceCode.getExpiresAt()),
                    TokenHelper.claimsHolderConsumer(deviceCode.getMetadata())
            );
        }
        return builder.build();
    }

    /**
     * 将框架所需的类型转为redis中存储的类型
     *
     * @param authorization 框架所需的类型
     * @return redis中存储的类型
     */
    private RedisOAuth2Authorization toEntity(OAuth2Authorization authorization) {
        RedisOAuth2Authorization entity = new RedisOAuth2Authorization();
        entity.setId(authorization.getId());
        entity.setRegisteredClientId(authorization.getRegisteredClientId());
        entity.setPrincipalName(authorization.getPrincipalName());
        entity.setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());
        entity.setAuthorizedScopes(StringUtils.collectionToDelimitedString(authorization.getAuthorizedScopes(), ","));
        entity.setAttributes(TokenHelper.mapToClaimsHolder(authorization.getAttributes()));
        entity.setState(authorization.getAttribute(OAuth2ParameterNames.STATE));
        TokenHelper.authorizationCode(authorization.getToken(OAuth2AuthorizationCode.class)).ifPresent(entity::setAuthorizationCode);
        TokenHelper.accessToken(authorization.getToken(OAuth2AccessToken.class)).ifPresent(entity::setAccessToken);
        TokenHelper.refreshToken(authorization.getToken(OAuth2RefreshToken.class)).ifPresent(entity::setRefreshToken);
        TokenHelper.oidcIdToken(authorization.getToken(OidcIdToken.class)).ifPresent(entity::setOidcToken);
        TokenHelper.userCode(authorization.getToken(OAuth2UserCode.class)).ifPresent(entity::setUserCode);
        TokenHelper.deviceCode(authorization.getToken(OAuth2DeviceCode.class)).ifPresent(entity::setDeviceCode);
        return entity;
    }

    /**
     * 处理授权申请时的 GrantType
     *
     * @param authorizationGrantType 授权申请时的 GrantType
     * @return AuthorizationGrantType的实例
     */
    private static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType) {
        if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.REFRESH_TOKEN;
        } else if (AuthorizationGrantType.DEVICE_CODE.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.DEVICE_CODE;
        }
        // Custom authorization grant type
        return new AuthorizationGrantType(authorizationGrantType);
    }


}

