package com.xht.auth.security.web.authentication.phone.provider;

import com.xht.auth.security.web.authentication.AbstractXhtLoginAuthenticationProvider;
import com.xht.auth.security.web.authentication.form.token.XhtFormLoginToken;
import com.xht.framework.core.enums.LoginTypeEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 登录认证提供者
 *
 * @author xht
 **/
@Slf4j
public class XhtPhoneLoginAuthenticationProvider extends AbstractXhtLoginAuthenticationProvider<XhtFormLoginToken> {

    /**
     * 检索用户信息
     * <p>
     * 根据认证请求中的标识信息（如用户名、手机号等）从数据源中加载用户详情。
     * 这是认证流程的核心步骤，需要子类根据具体业务场景实现。
     * </p>
     *
     * @param authentication 认证请求对象，包含用于查找用户的标识信息
     * @return UserDetails 加载到的用户详情对象
     * @throws AuthenticationException 当用户不存在或加载失败时抛出异常
     */
    @Override
    protected UserDetails retrieveUser(XhtFormLoginToken authentication) throws AuthenticationException {
        try {
            UserDetails loadedUser = userDetailsService.loadUserByUsername(authentication.getName(), LoginTypeEnums.PHONE);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("用户服务详情实现类返回了 null ，这违反了接口约定。");
            }
            return loadedUser;
        } catch (UsernameNotFoundException | InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    /**
     * 创建认证成功后的认证对象
     * <p>
     * 在用户认证成功之后调用，用于创建最终的Authentication对象。
     * 通常会包含用户的权限信息和认证状态。子类需要根据业务需求实现此方法。
     * </p>
     *
     * @param authentication 原始认证请求对象
     * @param user 已验证的用户详情对象
     * @return T 认证成功后的认证对象，包含完整的认证信息
     */
    @Override
    protected XhtFormLoginToken createSuccessAuthentication(Authentication authentication, UserDetails user) {
        XhtFormLoginToken authenticated = XhtFormLoginToken.authenticated(user, user.getPassword(), user.getAuthorities());
        authenticated.setDetails(authentication.getDetails());
        return authenticated;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return XhtFormLoginToken.class.isAssignableFrom(authentication);
    }

}
