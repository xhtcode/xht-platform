package com.xht.auth.security.oauth2.client.converter;

import com.xht.auth.authentication.entity.Oauth2ThirdAccountEntity;
import com.xht.auth.constant.Oauth2PlatformEnums;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.common.enums.UserTypeEnums;
import com.xht.framework.exception.utils.ThrowUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * 描述：oauth2 三方登录用户信息转换策略接口
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2UserConverterContext {

    private final List<Oauth2UserConverterStrategy> oauth2UserConverterStrategies;

    private final BasicUserDetailsService basicUserDetailsService;

    private final RedisRepository redisRepository;

    /**
     * 执行转换
     *
     * @param userRequest 三方登录请求
     * @param oAuth2User 三方登录用户信息
     * @return 转换后的用户信息
     */
    public BasicUserDetails execute(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        ThrowUtils.notNull(userRequest, "userRequest cannot be null");
        ThrowUtils.notNull(oAuth2User, "oAuth2User cannot be null");
        Oauth2ThirdAccountEntity accountEntity = null;
        for (Oauth2UserConverterStrategy oauth2UserConverterStrategy : oauth2UserConverterStrategies) {
            if (Objects.nonNull(accountEntity)) {
                continue;
            }
            Oauth2PlatformEnums oauth2PlatformEnums = Oauth2PlatformEnums.of(userRequest);
            ThrowUtils.notNull(oauth2PlatformEnums, "oauth2PlatformEnums cannot be null");
            if (oauth2UserConverterStrategy.supports(oauth2PlatformEnums)) {
                accountEntity = oauth2UserConverterStrategy.convert(userRequest, oAuth2User);
            }
        }
        if (Objects.nonNull(accountEntity)) {
            OAuth2AccessToken accessToken = userRequest.getAccessToken();
            redisRepository.set(accountEntity.getOpenId(), accessToken.getTokenValue());
            return new BasicUserDetails(accountEntity.getUserId(), UserTypeEnums.USER,
                    accountEntity.getNickName(), accountEntity.getNickName(), null,new HashSet<>());
        }
        throw new OAuth2AuthenticationException("oauth2UserConverterStrategy not found");
    }

}
