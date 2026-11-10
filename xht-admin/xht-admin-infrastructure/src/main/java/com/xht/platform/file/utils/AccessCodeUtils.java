package com.xht.platform.file.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 描述： 分享提取码工具
 * <p>4~8 位字母数字，大小写不敏感；随机生成时排除易混淆字符</p>
 *
 * @author xht
 **/
public final class AccessCodeUtils {

    private static final String ALPHABET = "23456789ABCDEFGHJKMNPQRSTUVWXYZ";
    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z0-9]{4,8}$");
    private static final List<String> WEAK_CODES = List.of(
            "123456", "111111", "000000", "666666", "888888", "123456",
            "ABC123", "PASSWORD", "QWERTY", "A123456", "123123");
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(10);

    private AccessCodeUtils() {
        throw new IllegalStateException("工具类不允许实例化");
    }

    /**
     * 随机生成提取码
     *
     * @param length 长度
     * @return 提取码
     */
    public static String random(int length) {
        int len = Math.max(4, Math.min(8, length));
        StringBuilder builder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return builder.toString();
    }

    /**
     * 归一化提取码
     *
     * @param code 原始值
     * @return 大写去空格后的值
     */
    public static String normalize(String code) {
        return code == null ? null : code.trim().toUpperCase(Locale.ROOT);
    }

    /**
     * 校验自定义提取码
     *
     * @param code 提取码
     * @return 错误提示，合法返回 null
     */
    public static String validate(String code) {
        if (code == null || code.isBlank()) {
            return "提取码不能为空";
        }
        String normalized = normalize(code);
        if (!PATTERN.matcher(normalized).matches()) {
            return "提取码必须为 4~8 位字母数字";
        }
        if (WEAK_CODES.contains(normalized)) {
            return "提取码过于简单，请更换";
        }
        boolean same = normalized.chars().allMatch(c -> c == normalized.charAt(0));
        if (same) {
            return "提取码不能为重复字符";
        }
        if (normalized.length() < 6) {
            boolean hasLetter = normalized.chars().anyMatch(Character::isLetter);
            boolean hasDigit = normalized.chars().anyMatch(Character::isDigit);
            if (!hasLetter || !hasDigit) {
                return "4~5 位提取码必须同时包含字母和数字";
            }
        }
        return null;
    }

    /**
     * 编码提取码
     *
     * @param code 提取码
     * @return BCrypt 哈希
     */
    public static String encode(String code) {
        return ENCODER.encode(normalize(code));
    }

    /**
     * 校验提取码
     *
     * @param rawCode 明文
     * @param hash    哈希
     * @return true：匹配
     */
    public static boolean matches(String rawCode, String hash) {
        if (rawCode == null || hash == null) {
            return false;
        }
        try {
            return ENCODER.matches(normalize(rawCode), hash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
