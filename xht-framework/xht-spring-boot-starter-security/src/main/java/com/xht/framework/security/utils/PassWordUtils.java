package com.xht.framework.security.utils;

import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.security.exception.BasicAuthenticationException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * 密码工具类
 * 提供安全的盐值生成、密码加密、密码验证、随机初始密码生成能力，基于Spring Security PasswordEncoder实现
 *
 * @author xht
 **/
@Slf4j
public final class PassWordUtils implements ApplicationContextAware {

    @Getter
    private static PasswordEncoder passwordEncoder;

    /**
     * 盐值默认长度（字节），推荐16位（128位），兼顾安全性和性能
     */
    private static final int DEFAULT_SALT_LENGTH = 16;

    /**
     * 随机初始密码默认长度
     */
    private static final int DEFAULT_RANDOM_PASSWORD_LENGTH = 12;

    /**
     * 随机密码最小长度（避免弱密码）
     */
    private static final int MIN_PASSWORD_LENGTH = 8;

    /**
     * 安全随机数生成器（线程安全）
     */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * 随机密码字符集：包含大小写字母、数字、常用特殊字符（排除易混淆的l/1、0/O等）
     */
    private static final String PASSWORD_CHAR_POOL = "ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789!@#$%^&*()_+-=[]{}|,.?";   // 大写字母（排除I、O） 小写字母（排除l、o） 数字（排除0、1） 常用特殊字符

    /**
     * 新增：生成符合安全规范的随机初始密码（系统自动生成给用户的默认密码）
     * 密码包含：大小写字母、数字、特殊字符，避免易混淆字符，保证安全性
     *
     * @return 随机初始密码（默认长度12位）
     */
    public static String generatePassword() {
        return generatePassword(DEFAULT_RANDOM_PASSWORD_LENGTH);
    }

    /**
     * 新增：生成指定长度的随机初始密码（重载方法，支持自定义长度）
     *
     * @param length 密码长度（需≥8位）
     * @return 随机初始密码
     */
    public static String generatePassword(int length) {
        // 校验密码长度，避免过短的弱密码
        ThrowUtils.throwIf(length <= MIN_PASSWORD_LENGTH, "Password length must be at least " + MIN_PASSWORD_LENGTH + " characters");

        StringBuilder password = new StringBuilder(length);
        int charPoolLength = PASSWORD_CHAR_POOL.length();

        // 安全随机选取字符生成密码
        for (int i = 0; i < length; i++) {
            int randomIndex = SECURE_RANDOM.nextInt(charPoolLength);
            password.append(PASSWORD_CHAR_POOL.charAt(randomIndex));
        }

        String randomPassword = password.toString();
        log.info("Generated random initial password (length: {})", length);
        // 注意：日志仅记录长度，不打印密码原文，避免敏感信息泄露
        return randomPassword;
    }


    /**
     * 生成随机盐值（Base64编码，便于存储）
     * @return 安全的随机盐值（Base64字符串）
     */
    public static String generatePasswordSalt() {
        byte[] saltBytes = new byte[DEFAULT_SALT_LENGTH];
        SECURE_RANDOM.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);
        log.debug("Generated random salt (Base64): {}", salt);
        return salt;
    }

    /**
     * 构建密码+盐值后的密码
     *
     * @param rawPassword 原始明文密码
     * @return 密码+盐值拼接（格式：密码{盐值}，便于后续解析验证）
     */
    public static String buildSalt(String rawPassword, String salt) {
        ThrowUtils.hasText(rawPassword, () -> new BasicAuthenticationException("密码不能为空"));
        if (StringUtils.isEmpty(salt)) {
            return rawPassword;
        }
        return rawPassword + "{" + salt + "}";
    }

    /**
     * 生成加密密码（原始密码 + 盐值）
     * 核心逻辑：原始密码拼接盐值后加密，保证相同密码不同盐值加密结果不同
     *
     * @param rawPassword 原始明文密码
     * @return 加密后的密码（不可逆）
     */
    public static String encodePassword(String rawPassword) {
        return encodePassword(rawPassword, null);
    }

    /**
     * 生成加密密码（原始密码 + 盐值）
     * 核心逻辑：原始密码拼接盐值后加密，保证相同密码不同盐值加密结果不同
     *
     * @param rawPassword 原始明文密码
     * @param salt        盐值（建议使用generatePasswordSalt()生成）
     * @return 加密后的密码（不可逆）
     */
    public static String encodePassword(String rawPassword, String salt) {
        String encodedPassword = passwordEncoder.encode(buildSalt(rawPassword, salt));
        log.debug("Encoded password for raw password (masked) with salt: {}", salt);
        return encodedPassword;
    }

    /**
     * 验证密码是否匹配
     *
     * @param rawPassword     原始明文密码
     * @param encodedPassword 已加密的密码（存储在数据库中）
     * @return true-匹配，false-不匹配
     */
    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        return matchPassword(rawPassword, null, encodedPassword);
    }

    /**
     * 验证密码是否匹配
     *
     * @param rawPassword     原始明文密码
     * @param salt            加密时使用的盐值
     * @param encodedPassword 已加密的密码（存储在数据库中）
     * @return true-匹配，false-不匹配
     */
    public static boolean matchPassword(String rawPassword, String salt, String encodedPassword) {
        ThrowUtils.hasText(rawPassword, "Raw password must not be empty");
        ThrowUtils.hasText(encodedPassword, "Encoded password must not be empty");
        boolean isMatch = passwordEncoder.matches(buildSalt(rawPassword, salt), encodedPassword);
        log.debug("Password match result: {}", isMatch);
        return isMatch;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PassWordUtils.passwordEncoder = applicationContext.getBean(PasswordEncoder.class);
    }
}