package com.xht.auth.security.web.authentication;

import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import com.xht.framework.security.utils.PassWordUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * 抽象登录认证提供者
 *
 * @author xht
 **/
@Slf4j
@Setter
public abstract class AbstractXhtLoginAuthenticationProvider<T extends AbstractXhtAuthenticationToken> implements AuthenticationProvider {

    protected BasicUserDetailsService userDetailsService;

    private UserDetailsChecker preAuthenticationChecks;

    private UserDetailsChecker postAuthenticationChecks;

    /**
     * Performs authentication with the same contract as
     * {@link AuthenticationManager#authenticate(Authentication)}
     * .
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(AbstractXhtAuthenticationToken.class, authentication, () -> "AbstractUserDetailsAuthenticationProvider.onlySupports Only AbstractXhtAuthenticationToken is supported");
        T authenticationToken = (T) authentication;
        String username = authentication.getName();
        UserDetails user;
        try {
            user = retrieveUser(authenticationToken);
        } catch (UsernameNotFoundException e) {
            log.debug("Failed to find user {}", username);
            throw new BadCredentialsException("AbstractUserDetailsAuthenticationProvider.badCredentials Bad credentials");
        }
        Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
        performPreCheck(user, authenticationToken);
        this.postAuthenticationChecks.check(user);
        return createSuccessAuthentication(authentication, user);
    }

    /**
     * 执行认证前检查
     * <p>
     * 首先执行前置认证检查（如账户是否锁定、禁用、过期等），如果前置检查失败，
     * 仍会尝试执行附加认证检查以保留原始失败信息，然后抛出异常。
     * 如果前置检查通过，则继续执行附加认证检查（如密码验证）。
     * </p>
     *
     * @param user 用户详情对象
     * @param authentication 认证请求对象，包含认证所需的凭据信息
     */
    private void performPreCheck(UserDetails user, T authentication) {
        try {
            this.preAuthenticationChecks.check(user);
        } catch (AuthenticationException ex) {
            try {
                additionalAuthenticationChecks(user, authentication);
            } catch (AuthenticationException ignored) {
                // preserve the original failed check
            }
            throw ex;
        }
        additionalAuthenticationChecks(user, authentication);
    }

    /**
     * 执行附加认证检查
     * <p>
     * 验证用户提供的凭据是否正确。对于非手机登录类型，会校验密码的匹配性。
     * 手机登录类型跳过密码校验。如果凭据为空或密码不匹配，将抛出BadCredentialsException。
     * </p>
     *
     * @param userDetails 用户详情对象，包含存储的用户凭证信息
     * @param authentication 认证请求对象，包含用户输入的凭据
     * @throws AuthenticationException 当凭据为空或密码不匹配时抛出异常
     */
    private void additionalAuthenticationChecks(UserDetails userDetails, T authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            log.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException("AbstractUserDetailsAuthenticationProvider.badCredentials Bad credentials");
        }
        String rawPassword = authentication.getCredentials().toString();
        String passWordSalt = null;
        if (userDetails instanceof BasicUserDetails basicUserDetails) {
            passWordSalt = basicUserDetails.getPassWordSalt();
        }
        LoginTypeEnums loginType = authentication.getLoginType();
        if (Objects.equals(loginType, LoginTypeEnums.PHONE)) {
            return;
        }
        if (!PassWordUtils.matchPassword(rawPassword, passWordSalt, userDetails.getPassword())) {
            log.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException("AbstractUserDetailsAuthenticationProvider.badCredentials Bad credentials");
        }
    }

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
    protected abstract UserDetails retrieveUser(T authentication) throws AuthenticationException;

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
    protected abstract T createSuccessAuthentication(Authentication authentication, UserDetails user);

}
