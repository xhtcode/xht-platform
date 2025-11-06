package com.xht.framework.oauth2.introspection;

import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.enums.UserTypeEnums;
import com.xht.framework.security.constant.TokenCustomizerIdConstant;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.xht.framework.core.utils.ConverterUtils.strToLong;

/**
 * 资源服务器Opaque Token Introspector
 *
 * @author xht
 **/
@Slf4j
public class ResourceOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final SpringOpaqueTokenIntrospector opaqueTokenIntrospector;

    public ResourceOpaqueTokenIntrospector(OAuth2ResourceServerProperties.Opaquetoken opaquetoken) {
        this.opaqueTokenIntrospector = SpringOpaqueTokenIntrospector.withIntrospectionUri(opaquetoken.getIntrospectionUri()).clientId(opaquetoken.getClientId()).clientSecret(opaquetoken.getClientSecret()).build();
    }

    /**
     * @param token 令牌
     * @return OAuth2AuthenticatedPrincipal
     */
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        log.info(">>>>>>资源服务器Opaque Token Introspector 验证令牌 {} <<<<<<", token);
        OAuth2AuthenticatedPrincipal introspect = opaqueTokenIntrospector.introspect(token);
        Map<String, Object> attributes = introspect.getAttributes();
        if (introspect instanceof OAuth2IntrospectionAuthenticatedPrincipal principal) {
            return convert(principal);
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
        List<String> permissionCodes = principal.getClaimAsStringList(TokenCustomizerIdConstant.PERMISSION_CODES);
        Integer dataScope = principal.getAttribute(TokenCustomizerIdConstant.DATA_SCOPE);
        LoginTypeEnums loginType = LoginTypeEnums.getByValue(principal.getClaimAsString(TokenCustomizerIdConstant.LOGIN_TYPE));
        BasicUserDetails details = new BasicUserDetails(userId, userType, username, nickName, null, Collections.emptySet());
        details.setUserStatus(userStatus);
        details.setUserPhone(userPhone);
        details.setDeptId(deptId);
        details.setDeptName(deptName);
        details.setPostId(postId);
        details.setRoleCodes(roleCodes);
        details.setPermissionCodes(permissionCodes);
        details.setDataScope(dataScope);
        details.setLoginType(loginType);
        return details;
    }

}
