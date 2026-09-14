package com.xht.framework.security.utils;

import com.xht.framework.utils.ThrowUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

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
     * 生成加密密码（原始密码 + 盐值）
     * 核心逻辑：原始密码拼接盐值后加密，保证相同密码不同盐值加密结果不同
     *
     * @param rawPassword 原始明文密码
     * @return 加密后的密码（不可逆）
     */
    public static String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 验证密码是否匹配
     *
     * @param rawPassword     原始明文密码
     * @param encodedPassword 已加密的密码（存储在数据库中）
     * @return true-匹配，false-不匹配
     */
    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        ThrowUtils.hasText(rawPassword, "Raw password must not be empty");
        ThrowUtils.hasText(encodedPassword, "Encoded password must not be empty");
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PassWordUtils.passwordEncoder = applicationContext.getBean(PasswordEncoder.class);
    }
}