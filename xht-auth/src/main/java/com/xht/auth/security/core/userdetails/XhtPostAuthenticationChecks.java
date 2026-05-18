package com.xht.auth.security.core.userdetails;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

@Slf4j
public class XhtPostAuthenticationChecks implements UserDetailsChecker {

    @Override
    public void check(UserDetails user) {
        if (!user.isCredentialsNonExpired()) {
            log.debug("认证失败，用户账户凭证已过期");
            throw new CredentialsExpiredException("用户凭证已过期");
        }
    }

}