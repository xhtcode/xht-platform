package com.xht.framework.security.crypto.password;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class BasicPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    /**
     * 密码校验
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return true or false
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        log.info("rawPassword:{},encodedPassword:{}", rawPassword, encodedPassword);
        return true;
    }
}