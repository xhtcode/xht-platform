package com.xht.auth.security.web.authentication.form.provider;

import com.xht.auth.security.web.authentication.AbstractXhtLoginAuthenticationProvider;
import com.xht.auth.security.web.authentication.form.token.XhtFormLoginToken;
import com.xht.framework.security.utils.PassWordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 登录认证提供者
 *
 * @author xht
 **/
@Slf4j
public class XhtFormLoginAuthenticationProvider extends AbstractXhtLoginAuthenticationProvider<XhtFormLoginToken> {

    /**
     * The plaintext password used to perform
     * {@link PasswordEncoder#matches(CharSequence, String)} on when the user is not found
     * to avoid SEC-2056.
     */
    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    /**
     * The password used to perform {@link PasswordEncoder#matches(CharSequence, String)}
     * on when the user is not found to avoid SEC-2056. This is necessary, because some
     * {@link PasswordEncoder} implementations will short circuit if the password is not
     * in a valid format.
     */
    private volatile String userNotFoundEncodedPassword;

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
        prepareTimingAttackProtection();
        try {
            UserDetails loadedUser = userDetailsService.loadUserByUsername(authentication.getName());
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("用户服务详情实现类返回了 null ，这违反了接口约定。");
            }
            return loadedUser;
        } catch (UsernameNotFoundException ex) {
            mitigateAgainstTimingAttack(authentication);
            throw ex;
        } catch (InternalAuthenticationServiceException ex) {
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

    /**
     * 准备时序攻击防护
     * <p>
     * 初始化一个预编码的"用户未找到"密码，用于在用户不存在时执行与正常密码验证相同耗时的操作。
     * 这样可以防止攻击者通过响应时间差异来判断用户是否存在。
     * </p>
     */
    private void prepareTimingAttackProtection() {
        if (this.userNotFoundEncodedPassword == null) {
            this.userNotFoundEncodedPassword = PassWordUtils.encodePassword(USER_NOT_FOUND_PASSWORD);
        }
    }

    /**
     * 缓解时序攻击
     * <p>
     * 即使用户不存在，也执行一次密码匹配操作，确保认证失败时的响应时间与用户存在但密码错误时相同。
     * 通过这种方式防止攻击者利用响应时间差异进行用户枚举攻击。
     * </p>
     *
     * @param authentication 表单登录认证令牌对象，包含用户提交的凭据信息
     */
    private void mitigateAgainstTimingAttack(XhtFormLoginToken authentication) {
        if (authentication.getCredentials() != null) {
            String presentedPassword = authentication.getCredentials().toString();
            PassWordUtils.matchPassword(presentedPassword, this.userNotFoundEncodedPassword);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return XhtFormLoginToken.class.isAssignableFrom(authentication);
    }

}
