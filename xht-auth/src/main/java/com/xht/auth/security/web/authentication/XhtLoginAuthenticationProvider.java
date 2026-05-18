package com.xht.auth.security.web.authentication;

import com.xht.auth.security.web.authentication.form.XhtFormLoginToken;
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
public class XhtLoginAuthenticationProvider extends AbstractXhtLoginAuthenticationProvider<XhtFormLoginToken> {

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

    @Override
    protected XhtFormLoginToken createSuccessAuthentication(Authentication authentication, UserDetails user) {
        XhtFormLoginToken authenticated = XhtFormLoginToken.authenticated(user, user.getPassword(), user.getAuthorities());
        authenticated.setDetails(authentication.getDetails());
        return authenticated;
    }

    private void prepareTimingAttackProtection() {
        if (this.userNotFoundEncodedPassword == null) {
            this.userNotFoundEncodedPassword = PassWordUtils.encodePassword(USER_NOT_FOUND_PASSWORD);
        }
    }

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
