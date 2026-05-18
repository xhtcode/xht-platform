package com.xht.auth.security.core.userdetails;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

@Slf4j
public class XhtPreAuthenticationChecks implements UserDetailsChecker {

    @Override
    public void check(UserDetails user) {
        if (!user.isAccountNonLocked()) {
            log.debug("认证失败，用户账户已被锁定");
            throw new LockedException("用户账户已被锁定");
        }
        if (!user.isEnabled()) {
            log.debug("认证失败，用户账户已禁用");
            throw new DisabledException("用户已禁用");
        }
        if (!user.isAccountNonExpired()) {
            log.debug("认证失败，用户账户已过期");
            throw new AccountExpiredException("用户账户已过期");
        }
    }

}