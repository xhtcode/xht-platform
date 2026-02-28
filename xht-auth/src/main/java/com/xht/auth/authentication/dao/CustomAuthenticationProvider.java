package com.xht.auth.authentication.dao;

import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.security.utils.PassWordUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
        super(userDetailsService);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        String rawPassword = authentication.getCredentials().toString();
        String passWordSalt = null;
        if (userDetails instanceof BasicUserDetails basicUserDetails) {
            passWordSalt = basicUserDetails.getPassWordSalt();
        }
        if (!PassWordUtils.matchPassword(rawPassword, passWordSalt, userDetails.getPassword())) {
            this.logger.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }

}