package com.xht.auth.security.web.authentication;

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

    protected abstract T createSuccessAuthentication(Authentication authentication, UserDetails user);

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

    protected abstract UserDetails retrieveUser(T authentication) throws AuthenticationException;

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
        if (!PassWordUtils.matchPassword(rawPassword, passWordSalt, userDetails.getPassword())) {
            log.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException("AbstractUserDetailsAuthenticationProvider.badCredentials Bad credentials");
        }
    }

}
