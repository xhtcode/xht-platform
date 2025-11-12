package com.xht.framework.oauth2.introspection;

import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.enums.UserTypeEnums;
import com.xht.framework.oauth2.token.TokenInfoLightningCache;
import com.xht.framework.security.constant.TokenCustomizerIdConstant;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static com.xht.framework.core.utils.ConverterUtils.strToLong;

/**
 * 资源服务器Opaque Token Introspector
 *
 * @author xht
 **/
@Slf4j
public class ResourceOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final SpringOpaqueTokenIntrospector opaqueTokenIntrospector;

    private final TokenInfoLightningCache tokenInfoLightningCache;

    private final static String TOKEN_KEY_PREFIX = "opaque:token:";


    public ResourceOpaqueTokenIntrospector(TokenInfoLightningCache tokenInfoLightningCache, OAuth2ResourceServerProperties.Opaquetoken opaquetoken) {
        this.tokenInfoLightningCache = tokenInfoLightningCache;
        this.opaqueTokenIntrospector = SpringOpaqueTokenIntrospector.withIntrospectionUri(opaquetoken.getIntrospectionUri()).clientId(opaquetoken.getClientId()).clientSecret(opaquetoken.getClientSecret()).build();
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return OAuth2AuthenticatedPrincipal
     */
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        log.debug(">>>>>>资源服务器Opaque Token Introspector 验证令牌 {} <<<<<<", token);
        String key = TOKEN_KEY_PREFIX + token;
        BasicUserDetails tokenInfo = tokenInfoLightningCache.getTokenInfo(key);
        if (Objects.nonNull(tokenInfo)) {
            return tokenInfo;
        }
        OAuth2AuthenticatedPrincipal introspect = opaqueTokenIntrospector.introspect(token);
        if (introspect instanceof OAuth2IntrospectionAuthenticatedPrincipal principal) {
            Instant expiresAt = principal.getExpiresAt();
            BasicUserDetails convert = convert(principal);
            tokenInfoLightningCache.setTokenInfo(key, expiresAt, convert);
            return convert;
        }
        return introspect;
    }

    /**
     * 将OAuth2认证后的用户主体信息转换为BasicUserDetails对象
     *
     * @param principal OAuth2认证后的用户主体信息，包含用户的各种属性和权限信息
     * @return 转换后的BasicUserDetails用户详情对象，包含用户的基本信息、权限、角色等
     */
    private BasicUserDetails convert(OAuth2IntrospectionAuthenticatedPrincipal principal) {
        Long userId = strToLong(principal.getClaimAsString(TokenCustomizerIdConstant.USER_ID));
        UserTypeEnums userType = UserTypeEnums.getByValue(principal.getAttribute(TokenCustomizerIdConstant.USER_TYPE));
        String username = principal.getClaimAsString(TokenCustomizerIdConstant.USER_NAME);
        String nickName = principal.getClaimAsString(TokenCustomizerIdConstant.NICK_NAME);
        UserStatusEnums userStatus = UserStatusEnums.getByValue(principal.getAttribute(TokenCustomizerIdConstant.USER_STATUS));
        String userPhone = principal.getClaimAsString(TokenCustomizerIdConstant.USER_PHONE);
        Long deptId = strToLong(principal.getClaimAsString(TokenCustomizerIdConstant.DEPT_ID));
        String deptName = principal.getClaimAsString(TokenCustomizerIdConstant.DEPT_NAME);
        Long postId = strToLong(principal.getClaimAsString(TokenCustomizerIdConstant.POST_ID));
        List<String> roleCodes = principal.getClaimAsStringList(TokenCustomizerIdConstant.ROLE_CODES);
        List<String> menuButtonCodes = principal.getClaimAsStringList(TokenCustomizerIdConstant.MENU_BUTTON_CODE);
        Integer dataScope = principal.getAttribute(TokenCustomizerIdConstant.DATA_SCOPE);
        LoginTypeEnums loginType = LoginTypeEnums.getByValue(principal.getClaimAsString(TokenCustomizerIdConstant.LOGIN_TYPE));
        BasicUserDetails details = new BasicUserDetails(userId, userType, username, nickName, null, Collections.emptySet());
        details.setUserStatus(userStatus);
        details.setUserPhone(userPhone);
        details.setDeptId(deptId);
        details.setDeptName(deptName);
        details.setPostId(postId);
        if (!CollectionUtils.isEmpty(roleCodes)) {
            details.setRoleCodes(new HashSet<>(roleCodes));
        } else {
            details.setRoleCodes(Collections.emptySet());
        }
        if (!CollectionUtils.isEmpty(menuButtonCodes)) {
            details.setMenuButtonCodes(new HashSet<>(menuButtonCodes));
        } else {
            details.setMenuButtonCodes(Collections.emptySet());
        }
        details.setDataScope(dataScope);
        details.setLoginType(loginType);
        return details;
    }

}
