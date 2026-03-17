package com.xht.framework.security.core.userdetails;

import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.security.domain.RequestUserBO;
import com.xht.framework.security.utils.PassWordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

/**
 * 用户查询dao
 *
 * @author xht
 **/
@Slf4j
public abstract class BasicUserDetailsService implements UserDetailsService {

    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    private volatile String userNotFoundEncodedPassword;

    @Override
    public final UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByUsername(username, LoginTypeEnums.PASSWORD);
    }

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     * @throws UsernameNotFoundException 异常
     */
    public abstract BasicUserDetails loadUserByUsername(String username, LoginTypeEnums loginType) throws UsernameNotFoundException;


    /**
     * 校验用户信息
     *
     * @param requestUserBO         请求信息
     * @param basicUserDetails      用户信息
     * @param passWordEncoderStatus 是否需要密码匹配
     * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
     */
    public final void validate(RequestUserBO requestUserBO, BasicUserDetails basicUserDetails, boolean passWordEncoderStatus) {
        try {
            prepareTimingAttackProtection();
            if (Objects.isNull(basicUserDetails)) {
                throw new UsernameNotFoundException("用户名或密码错误");
            }
            if (basicUserDetails.getPassword() == null) {
                log.debug("由于密码与存储的值不匹配，因此无法进行身份验证。");
                throw new BadCredentialsException("用户名或密码错误");
            }
            if (!basicUserDetails.isAccountNonLocked()) {
                log.debug("由于用户账户已被锁定，因此无法进行身份验证。");
                throw new LockedException("用户账户已被锁定");
            }
            if (!basicUserDetails.isEnabled()) {
                log.debug("由于用户账户已被禁用，因此无法进行身份验证。");
                throw new DisabledException("用户账户已被禁用");
            }
            if (!basicUserDetails.isAccountNonExpired()) {
                log.debug("由于用户账户已过期，无法进行身份验证。");
                throw new AccountExpiredException("用户账户已过期");
            }
            if (passWordEncoderStatus) {
                if (!PassWordUtils.matchPassword(requestUserBO.getPassWord(), basicUserDetails.getPassWordSalt(), basicUserDetails.getPassword())) {
                    log.debug("由于密码与存储的值不匹配，因此无法进行身份验证。");
                    throw new BadCredentialsException("用户名或密码错误");
                }
            } else {
                mitigateAgainstTimingAttack(requestUserBO);
            }
            if (!basicUserDetails.isCredentialsNonExpired()) {
                log.debug("由于用户账户的密码已过期，因此无法进行身份验证。d");
                throw new CredentialsExpiredException("用户账号已过期");
            }
        } catch (Exception e) {
            mitigateAgainstTimingAttack(requestUserBO);
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    /**
     * 密码匹配
     */
    private void prepareTimingAttackProtection() {
        if (this.userNotFoundEncodedPassword == null) {
            this.userNotFoundEncodedPassword = PassWordUtils.encodePassword(USER_NOT_FOUND_PASSWORD);
        }
    }

    /**
     * 密码匹配
     */
    private void mitigateAgainstTimingAttack(RequestUserBO requestUserBO) {
        if (requestUserBO.getPassWord() != null) {
            String presentedPassword = requestUserBO.getPassWord();
            PassWordUtils.matchPassword(presentedPassword, this.userNotFoundEncodedPassword);
        }
    }

}
