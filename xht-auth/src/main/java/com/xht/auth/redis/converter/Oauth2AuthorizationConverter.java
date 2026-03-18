package com.xht.auth.redis.converter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.xht.auth.redis.entity.Oauth2AuthorizationEntity;
import com.xht.framework.core.converter.IConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Oauth2AuthorizationEntity 转换器
 *
 * @author xht
 **/
@Slf4j
public final class Oauth2AuthorizationConverter implements IConverter<OAuth2Authorization, Oauth2AuthorizationEntity> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RegisteredClientRepository registeredClientRepository;

    private final TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
    };

    public Oauth2AuthorizationConverter() {
        registeredClientRepository = new VirtualRegisteredClientRepository();
        // 序列化所有字段
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 此项必须配置，否则如果序列化的对象里边还有对象，会报如下错误：
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        ClassLoader classLoader = Oauth2AuthorizationConverter.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        this.objectMapper.registerModules(securityModules);
        this.objectMapper.registerModule(new CoreJackson2Module());
        this.objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
    }

    /**
     * 将源对象转换为目标对象
     *
     * @param authorization 源对象，非null
     * @return 转换后的目标对象，非null
     */
    @Override
    public Oauth2AuthorizationEntity convert(OAuth2Authorization authorization) {
        // 过期时间，默认永不过期
        long maxTimeout = -1L;
        List<Instant> expiresAtList = new ArrayList<>();
        Oauth2AuthorizationEntity entity = new Oauth2AuthorizationEntity();
        entity.setId(authorization.getId());
        entity.setRegisteredClientId(authorization.getRegisteredClientId());
        entity.setPrincipalName(authorization.getPrincipalName());
        entity.setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());
        entity.setAuthorizedScopes(StringUtils.collectionToDelimitedString(authorization.getAuthorizedScopes(), ","));
        entity.setAttributes(writeMap(authorization.getAttributes()));
        entity.setState(authorization.getAttribute(OAuth2ParameterNames.STATE));

        // 提取并设置授权码相关信息
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
        if (Objects.nonNull(authorizationCode)) {
            OAuth2AuthorizationCode token = authorizationCode.getToken();
            entity.setAuthorizationCodeValue(token.getTokenValue());
            entity.setAuthorizationCodeIssuedAt(instantToTime(token.getIssuedAt()));
            entity.setAuthorizationCodeExpiresAt(instantToTime(token.getExpiresAt()));
            entity.setAuthorizationCodeMetadata(writeMap(authorizationCode.getMetadata()));
            expiresAtList.add(token.getExpiresAt());
        }

        // 提取并设置访问令牌相关信息
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getToken(OAuth2AccessToken.class);
        if (Objects.nonNull(accessToken)) {
            OAuth2AccessToken token = accessToken.getToken();
            entity.setAccessTokenValue(token.getTokenValue());
            entity.setAccessTokenIssuedAt(instantToTime(token.getIssuedAt()));
            entity.setAccessTokenExpiresAt(instantToTime(token.getExpiresAt()));
            entity.setAccessTokenMetadata(writeMap(accessToken.getMetadata()));
            entity.setAccessTokenType(token.getTokenType().getValue());
            entity.setAccessTokenScopes(StringUtils.collectionToDelimitedString(token.getScopes(), ","));
            expiresAtList.add(token.getExpiresAt());
        }

        // 提取并设置 OIDC ID 令牌相关信息
        OAuth2Authorization.Token<OidcIdToken> oidcIdToken = authorization.getToken(OidcIdToken.class);
        if (Objects.nonNull(oidcIdToken)) {
            OidcIdToken token = oidcIdToken.getToken();
            entity.setOidcIdTokenValue(token.getTokenValue());
            entity.setOidcIdTokenIssuedAt(instantToTime(token.getIssuedAt()));
            entity.setOidcIdTokenExpiresAt(instantToTime(token.getExpiresAt()));
            entity.setOidcIdTokenMetadata(writeMap(oidcIdToken.getMetadata()));
            expiresAtList.add(token.getExpiresAt());

        }

        // 提取并设置刷新令牌相关信息
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getToken(OAuth2RefreshToken.class);
        if (Objects.nonNull(refreshToken)) {
            OAuth2RefreshToken token = refreshToken.getToken();
            entity.setRefreshTokenValue(token.getTokenValue());
            entity.setRefreshTokenIssuedAt(instantToTime(token.getIssuedAt()));
            entity.setRefreshTokenExpiresAt(instantToTime(token.getExpiresAt()));
            entity.setRefreshTokenMetadata(writeMap(refreshToken.getMetadata()));
            expiresAtList.add(token.getExpiresAt());
        }

        // 提取并设置用户码相关信息（用于设备授权模式）
        OAuth2Authorization.Token<OAuth2UserCode> userCode = authorization.getToken(OAuth2UserCode.class);
        if (Objects.nonNull(userCode)) {
            OAuth2UserCode token = userCode.getToken();
            entity.setUserCodeValue(token.getTokenValue());
            entity.setUserCodeIssuedAt(instantToTime(token.getIssuedAt()));
            entity.setUserCodeExpiresAt(instantToTime(token.getExpiresAt()));
            entity.setUserCodeMetadata(writeMap(userCode.getMetadata()));
            expiresAtList.add(token.getExpiresAt());
        }

        // 提取并设置设备码相关信息（用于设备授权模式）
        OAuth2Authorization.Token<OAuth2DeviceCode> deviceCode = authorization.getToken(OAuth2DeviceCode.class);
        if (Objects.nonNull(deviceCode)) {
            OAuth2DeviceCode token = deviceCode.getToken();
            entity.setDeviceCodeValue(token.getTokenValue());
            entity.setDeviceCodeIssuedAt(instantToTime(token.getIssuedAt()));
            entity.setDeviceCodeExpiresAt(instantToTime(token.getExpiresAt()));
            entity.setDeviceCodeMetadata(writeMap(deviceCode.getMetadata()));
            expiresAtList.add(token.getExpiresAt());
        }
        // 获取最大的日期
        Optional<Instant> maxInstant = expiresAtList.stream().max(Comparator.comparing(Instant::getEpochSecond));
        if (maxInstant.isPresent()) {
            // 计算时间差
            Duration between = Duration.between(Instant.now(), maxInstant.get());
            // 转为分钟
            maxTimeout = between.toMinutes();
        }
        entity.setCreateTime(LocalDateTime.now());
        entity.setTimeout(maxTimeout);
        return entity;
    }


    /**
     * 将目标对象反转转换为源对象
     *
     * @param entity 目标对象，非null
     * @return 反转转换后的源对象，非null
     */
    @Override
    @SuppressWarnings("unchecked")
    public OAuth2Authorization reverse(Oauth2AuthorizationEntity entity) {
        // 获取客户端标识并查找对应的RegisteredClient
        String registeredClientId = entity.getRegisteredClientId();
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(registeredClientId);
        if (registeredClient == null) {
            throw new DataRetrievalFailureException("The RegisteredClient with id '" + registeredClientId + "' was not found in the RegisteredClientRepository.");
        }

        // 初始化OAuth2Authorization构建器
        OAuth2Authorization.withRegisteredClient(registeredClient)
                .attribute(Principal.class.getName(), entity.getAuthorizationGrantType());
        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient);
        // 设置基本属性：ID、主体名称、授权类型和授权范围
        String id = entity.getId();
        String principalName = entity.getPrincipalName();
        String authorizationGrantType = entity.getAuthorizationGrantType();
        Set<String> authorizedScopes = Collections.emptySet();
        String authorizedScopesString = entity.getAuthorizedScopes();
        if (authorizedScopesString != null) {
            authorizedScopes = StringUtils.commaDelimitedListToSet(authorizedScopesString);
        }

        // 解析并设置属性(attributes)
        Map<String, Object> attributes = parseMap(entity.getAttributes());
        builder.id(id)
                .principalName(principalName)
                .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
                .authorizedScopes(authorizedScopes)
                .attributes((attrs) -> attrs.putAll(attributes));

        // 设置state参数（如果存在）
        String state = entity.getState();
        if (StringUtils.hasText(state)) {
            builder.attribute(OAuth2ParameterNames.STATE, state);
        }

        // 构建并设置授权码（authorization code）令牌
        String authorizationCodeValue = entity.getAuthorizationCodeValue();
        if (StringUtils.hasText(authorizationCodeValue)) {
            Map<String, Object> authorizationCodeMetadata = parseMap(entity.getAuthorizationCodeMetadata());
            Instant authorizationCodeIssuedAt = timeToInstant(entity.getAuthorizationCodeIssuedAt());
            Instant authorizationCodeExpiresAt = timeToInstant(entity.getAuthorizationCodeExpiresAt());
            OAuth2AuthorizationCode authorizationCode = new OAuth2AuthorizationCode(authorizationCodeValue, authorizationCodeIssuedAt, authorizationCodeExpiresAt);
            builder.token(authorizationCode, (metadata) -> metadata.putAll(authorizationCodeMetadata));
        }

        // 构建并设置访问令牌（access token）
        String accessTokenValue = entity.getAccessTokenValue();
        if (StringUtils.hasText(accessTokenValue)) {
            Map<String, Object> accessTokenMetadata = parseMap(entity.getAccessTokenMetadata());
            OAuth2AccessToken.TokenType tokenType;
            if (OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(entity.getAccessTokenType())) {
                tokenType = OAuth2AccessToken.TokenType.BEARER;
            } else {
                tokenType = OAuth2AccessToken.TokenType.DPOP;
            }
            Set<String> scopes = Collections.emptySet();
            String accessTokenScopes = entity.getAccessTokenScopes();
            if (accessTokenScopes != null) {
                scopes = StringUtils.commaDelimitedListToSet(accessTokenScopes);
            }
            Instant accessTokenIssuedAt = timeToInstant(entity.getAccessTokenIssuedAt());
            Instant accessTokenExpiresAt = timeToInstant(entity.getAccessTokenExpiresAt());
            OAuth2AccessToken accessToken = new OAuth2AccessToken(tokenType, accessTokenValue, accessTokenIssuedAt, accessTokenExpiresAt, scopes);
            builder.token(accessToken, (metadata) -> metadata.putAll(accessTokenMetadata));
        }

        // 构建并设置OIDC ID令牌
        String oidcIdTokenValue = entity.getOidcIdTokenValue();
        if (StringUtils.hasText(oidcIdTokenValue)) {
            Map<String, Object> oidcTokenMetadata = parseMap(entity.getOidcIdTokenMetadata());
            Instant oidcIdTokenIssuedAt = timeToInstant(entity.getOidcIdTokenIssuedAt());
            Instant oidcIdTokenExpiresAt = timeToInstant(entity.getOidcIdTokenExpiresAt());
            OidcIdToken oidcToken = new OidcIdToken(oidcIdTokenValue, oidcIdTokenIssuedAt, oidcIdTokenExpiresAt,
                    (Map<String, Object>) oidcTokenMetadata.get(OAuth2Authorization.Token.CLAIMS_METADATA_NAME));
            builder.token(oidcToken, (metadata) -> metadata.putAll(oidcTokenMetadata));
        }

        // 构建并设置刷新令牌（refresh token）
        String refreshTokenValue = entity.getRefreshTokenValue();
        if (StringUtils.hasText(refreshTokenValue)) {
            Map<String, Object> refreshTokenMetadata = parseMap(entity.getRefreshTokenMetadata());
            Instant refreshTokenIssuedAt = timeToInstant(entity.getRefreshTokenIssuedAt());
            Instant refreshTokenExpiresAt = timeToInstant(entity.getRefreshTokenExpiresAt());
            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(refreshTokenValue, refreshTokenIssuedAt, refreshTokenExpiresAt);
            builder.token(refreshToken, (metadata) -> metadata.putAll(refreshTokenMetadata));
        }

        // 构建并设置用户码（user code），用于设备授权流程
        String userCodeValue = entity.getUserCodeValue();
        if (StringUtils.hasText(userCodeValue)) {
            Map<String, Object> userCodeMetadata = parseMap(entity.getUserCodeMetadata());
            Instant userCodeIssuedAt = timeToInstant(entity.getUserCodeIssuedAt());
            Instant userCodeExpiresAt = timeToInstant(entity.getUserCodeExpiresAt());
            OAuth2UserCode userCode = new OAuth2UserCode(userCodeValue, userCodeIssuedAt, userCodeExpiresAt);
            builder.token(userCode, (metadata) -> metadata.putAll(userCodeMetadata));
        }

        // 构建并设置设备码（device code），用于设备授权流程
        String deviceCodeValue = entity.getDeviceCodeValue();
        if (StringUtils.hasText(deviceCodeValue)) {
            Map<String, Object> deviceCodeMetadata = parseMap(entity.getDeviceCodeMetadata());
            Instant deviceCodeIssuedAt = timeToInstant(entity.getDeviceCodeIssuedAt());
            Instant deviceCodeExpiresAt = timeToInstant(entity.getDeviceCodeExpiresAt());
            OAuth2DeviceCode deviceCode = new OAuth2DeviceCode(deviceCodeValue, deviceCodeIssuedAt, deviceCodeExpiresAt);
            builder.token(deviceCode, (metadata) -> metadata.putAll(deviceCodeMetadata));
        }

        // 返回最终构建的OAuth2Authorization对象
        return builder.build();
    }


    /**
     * 将JSON字符串解析为Map对象
     *
     * @param data 待解析的JSON字符串
     * @return 解析后的Map对象
     * @throws IllegalArgumentException 当解析失败时抛出
     */
    private Map<String, Object> parseMap(String data) {
        try {
            if (Objects.isNull(data)) {
                return Collections.emptyMap();
            }
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            JavaType javaType = typeFactory.constructType(typeRef.getType());
            return this.objectMapper.readValue(data, javaType);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * 将Map对象转换为JSON字符串
     *
     * @param data 待转换的Map对象
     * @return 转换后的JSON字符串，如果输入为空则返回null
     * @throws IllegalArgumentException 当转换失败时抛出
     */
    private String writeMap(Map<String, Object> data) {
        try {
            // 检查数据是否为空，避免不必要的序列化操作
            if (CollectionUtils.isEmpty(data)) {
                return null;
            }
            return this.objectMapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * 将Instant时间转换为LocalDateTime
     *
     * @param instant 待转换的Instant时间
     * @return 转换后的LocalDateTime，如果输入为null则返回null
     */
    private LocalDateTime instantToTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 将LocalDateTime转换为Instant时间
     *
     * @param time 待转换的LocalDateTime
     * @return 转换后的Instant时间，如果输入为null则返回null
     */
    private Instant timeToInstant(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return time.atZone(ZoneId.systemDefault()).toInstant();
    }

    private static class VirtualRegisteredClientRepository implements RegisteredClientRepository {

        @Override
        public void save(RegisteredClient registeredClient) {
            throw new UnsupportedOperationException();
        }

        @Override
        public RegisteredClient findById(String id) {
            throw new UnsupportedOperationException();
        }


        @Override
        public RegisteredClient findByClientId(String clientId) {
            return RegisteredClient.withId(clientId).clientId(clientId)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri(clientId)
                    .build();
        }
    }

}
