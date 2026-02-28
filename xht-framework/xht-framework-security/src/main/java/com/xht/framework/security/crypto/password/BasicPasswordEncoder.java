package com.xht.framework.security.crypto.password;

import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.secret.MD5Utils;
import com.xht.framework.security.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Slf4j
public class BasicPasswordEncoder implements PasswordEncoder {

    /**
     * 对原始密码进行编码
     *
     * @param rawPassword 原始密码字符序列
     * @return 编码后的密码字符串
     */
    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new BusinessException("原始密码不能为null");
        }
        // 增加密码强度校验
        if (rawPassword.length() < 6) {
            throw new BusinessException("密码长度不能少于6位");
        }
        return MD5Utils.generateSignature(rawPassword.toString());
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
        log.debug("rawPassword:{},encodedPassword:{}", rawPassword, encodedPassword);
        if (!StringUtils.hasText(rawPassword)) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        if (!StringUtils.hasText(encodedPassword)) {
            return false;
        }
        String[] passWord = StringUtils.split(encodedPassword, SecurityConstant.PASSWORD_JOIN_SALT);
        if (Objects.isNull(passWord) || passWord.length != 2) {
            return StringUtils.equals(encodedPassword, MD5Utils.generateSignature(rawPassword.toString()));
        }
        return StringUtils.equals(passWord[0], MD5Utils.generateSignature(rawPassword + "{" + passWord[1] + "}"));
    }
}
