package com.xht.auth.constant;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Optional;

/**
 * 描述：oauth2 平台
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum Oauth2PlatformEnums implements IEnum<String> {

    GITEE("gitee", "码云"),

    GITHUB("github", "github"),

    WECHAT("wechat", "微信");

    private final String value;

    private final String description;

    /**
     * 根据值获取枚举
     *
     * @param oAuth2UserRequest oAuth2UserRequest
     * @return Oauth2PlatformEnums
     */
    public static Oauth2PlatformEnums of(OAuth2UserRequest oAuth2UserRequest) {
        String value = Optional.ofNullable(oAuth2UserRequest).map(OAuth2UserRequest::getClientRegistration).map(ClientRegistration::getRegistrationId).orElse(null);
        for (Oauth2PlatformEnums oauth2PlatformEnums : values()) {
            if (oauth2PlatformEnums.getValue().equals(value)) {
                return oauth2PlatformEnums;
            }
        }
        return null;
    }

}
