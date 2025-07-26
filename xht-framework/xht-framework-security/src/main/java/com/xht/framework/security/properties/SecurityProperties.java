package com.xht.framework.security.properties;

import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.security.exception.BasicAuthenticationException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xht
 **/
@Slf4j
@Data
@ConfigurationProperties(prefix = "xht.security")
public class SecurityProperties {

    private static final String DEFAULT_SALT = "123456";

    private static final String PASSWORD_JOIN_SALT = "#";

    private Password password = new Password();

    public String getSalt() {
        return password.getSalt();
    }

    /**
     * 构建密码+盐值后的密码
     *
     * @param password 密码
     * @return 密码+盐值
     */
    public String buildSalt(String password, String salt) {
        ThrowUtils.throwIf(StringUtils.isEmpty(password), () -> new BasicAuthenticationException("密码不能为空"));
        salt = StringUtils.emptyToDefault(salt, getSalt());
        if (StringUtils.isEmpty(salt)) {
            return password;
        }
        return password + PASSWORD_JOIN_SALT + salt;
    }


    @Data
    public static class Password {

        /**
         * 密码加密使用的盐值
         */
        private String salt = DEFAULT_SALT;

    }
}
