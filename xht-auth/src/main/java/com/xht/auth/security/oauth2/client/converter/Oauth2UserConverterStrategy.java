package com.xht.auth.security.oauth2.client.converter;

import com.xht.auth.authentication.entity.Oauth2ThirdAccountEntity;
import com.xht.auth.constant.Oauth2PlatformEnums;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * 描述：oauth2 三方登录用户信息转换策略接口
 *
 * @author xht
 **/
public abstract class Oauth2UserConverterStrategy {

    /**
     * 将 OAuth2 用户信息转换为目标对象。
     * <p>
     * 子类应实现此方法，根据第三方 OAuth2 平台返回的用户请求和原始用户信息，
     * 提取并映射为业务所需的用户对象（如本地用户实体、DTO 等）。
     *
     * @param userRequest 三方登录请求
     * @param oAuth2User 三方登录用户信息
     * @return 转换后的业务用户对象
     */
    public abstract Oauth2ThirdAccountEntity convert(OAuth2UserRequest userRequest, OAuth2User oAuth2User);

    /**
     * 判断当前策略是否支持指定的 OAuth2 客户端注册。
     * <p>
     * 子类通过比对 {@code oauth2PlatformEnums}（如 "github"、"gitee"、"wechat" 等）
     * 来决定是否由本策略处理对应的用户信息转换逻辑。
     *
     * @param oauth2PlatformEnums OAuth2 客户端注册标识，对应配置中的 registration-id
     * @return 若当前策略支持该注册标识则返回 {@code true}，否则返回 {@code false}
     */
    public abstract boolean supports(Oauth2PlatformEnums oauth2PlatformEnums);
}
