package com.xht.framework.core.utils;

import com.xht.framework.core.enums.GenderEnums;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 身份证号码验证工具类
 * 支持15位和18位身份证号码的验证、信息提取及格式转换
 *
 * @author xht
 */
@SuppressWarnings("all")
public final class IdCardUtils {

    /**
     * 省份代码映射表（不可修改）
     */
    private static final Map<String, String> PROVINCE_CODES;

    /**
     * 身份证号码正则表达式（15位纯数字或18位数字+校验码）
     */
    private static final Pattern ID_CARD_PATTERN = Pattern.compile("^(\\d{15}|\\d{17}[0-9Xx])$");

    /**
     * 18位身份证校验码计算的权重因子
     */
    private static final int[] ID_CARD_WEIGHT_FACTORS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 校验码对应值（0-10分别对应如下字符）
     */
    private static final char[] CHECK_CODES = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    static {
        // 初始化省份代码映射并转为不可修改集合
        Map<String, String> provinceMap = new HashMap<>();
        provinceMap.put("11", "北京");
        provinceMap.put("12", "天津");
        provinceMap.put("13", "河北");
        provinceMap.put("14", "山西");
        provinceMap.put("15", "内蒙古");
        provinceMap.put("21", "辽宁");
        provinceMap.put("22", "吉林");
        provinceMap.put("23", "黑龙江");
        provinceMap.put("31", "上海");
        provinceMap.put("32", "江苏");
        provinceMap.put("33", "浙江");
        provinceMap.put("34", "安徽");
        provinceMap.put("35", "福建");
        provinceMap.put("36", "江西");
        provinceMap.put("37", "山东");
        provinceMap.put("41", "河南");
        provinceMap.put("42", "湖北");
        provinceMap.put("43", "湖南");
        provinceMap.put("44", "广东");
        provinceMap.put("45", "广西");
        provinceMap.put("46", "海南");
        provinceMap.put("50", "重庆");
        provinceMap.put("51", "四川");
        provinceMap.put("52", "贵州");
        provinceMap.put("53", "云南");
        provinceMap.put("54", "西藏");
        provinceMap.put("61", "陕西");
        provinceMap.put("62", "甘肃");
        provinceMap.put("63", "青海");
        provinceMap.put("64", "宁夏");
        provinceMap.put("65", "新疆");
        provinceMap.put("71", "台湾");
        provinceMap.put("81", "香港");
        provinceMap.put("82", "澳门");
        PROVINCE_CODES = Collections.unmodifiableMap(provinceMap);
    }

    /**
     * 私有构造函数，防止实例化
     */
    private IdCardUtils() {
        throw new AssertionError("工具类不允许实例化");
    }

    /**
     * 验证身份证号码是否合法
     *
     * @param idCard 身份证号码（可为null或空白）
     * @return true-合法，false-不合法
     */
    public static boolean isValid(String idCard) {
        // 快速校验空值和空白
        if (StringUtils.isEmpty(idCard)) {
            return false;
        }
        String trimmedId = idCard.trim();
        // 基本格式校验
        if (!ID_CARD_PATTERN.matcher(trimmedId).matches()) {
            return false;
        }

        // 省份代码校验
        String provinceCode = trimmedId.substring(0, 2);
        if (!PROVINCE_CODES.containsKey(provinceCode)) {
            return false;
        }

        // 根据长度进行专项校验
        return trimmedId.length() == 15
                ? isValid15DigitIdCard(trimmedId)
                : isValid18DigitIdCard(trimmedId);
    }

    /**
     * 验证15位身份证号码
     *
     * @param idCard 15位身份证号码（已trim且格式校验通过）
     * @return true-合法，false-不合法
     */
    private static boolean isValid15DigitIdCard(String idCard) {
        // 提取生日信息并验证（处理2000年后出生的情况）
        String birthdayStr = build15DigitBirthdayStr(idCard);
        return isValidBirthday(birthdayStr);
    }

    /**
     * 验证18位身份证号码
     *
     * @param idCard 18位身份证号码（已trim且格式校验通过）
     * @return true-合法，false-不合法
     */
    private static boolean isValid18DigitIdCard(String idCard) {
        // 验证出生日期
        String birthdayStr = idCard.substring(6, 14);
        if (!isValidBirthday(birthdayStr)) {
            return false;
        }

        // 验证校验码
        return verifyCheckCode(idCard);
    }

    /**
     * 构建15位身份证的生日字符串（处理年份转换）
     *
     * @param idCard 15位身份证号码
     * @return 格式为yyyyMMdd的生日字符串
     */
    private static String build15DigitBirthdayStr(String idCard) {
        // 15位身份证的6-8位为年份（两位），8-12位为月日
        String yyStr = idCard.substring(6, 8);
        String mmddStr = idCard.substring(8, 12);
        int yy = Integer.parseInt(yyStr);

        // 根据当前年份判断是19xx还是20xx（如2025年，yy<=25则为20xx，否则19xx）
        int currentYear = LocalDate.now().getYear();
        int currentYearTwoDigit = currentYear % 100;
        int yyyy = yy <= currentYearTwoDigit ? 2000 + yy : 1900 + yy;

        return yyyy + mmddStr;
    }

    /**
     * 验证出生日期是否合法
     *
     * @param birthday 出生日期字符串
     * @return true-合法，false-不合法
     */
    private static boolean isValidBirthday(String birthday) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birthDate = LocalDate.parse(birthday, formatter);

            // 合理的出生日期范围：1900年1月1日至当前日期
            LocalDate minDate = LocalDate.of(1900, 1, 1);
            LocalDate maxDate = LocalDate.now();

            return !birthDate.isBefore(minDate) && !birthDate.isAfter(maxDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * 验证18位身份证的校验码
     *
     * @param idCard 18位身份证号码
     * @return true-校验通过，false-校验失败
     */
    private static boolean verifyCheckCode(String idCard) {
        int mod = getMod(idCard);
        char actualCheckCode = Character.toUpperCase(idCard.charAt(17));
        return actualCheckCode == CHECK_CODES[mod];
    }

    /**
     * 获取身份证的校验码
     *
     * @param idCard 身份证好
     * @return 校验码
     */
    private static int getMod(String idCard) {
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            int digit = Character.getNumericValue(idCard.charAt(i));
            sum += digit * ID_CARD_WEIGHT_FACTORS[i];
        }
        // 计算校验码并忽略大小写比较
        return sum % 11;
    }

    /**
     * 从身份证号码提取出生日期
     *
     * @param idCard 身份证号码
     * @return 出生日期（Optional容器，空表示提取失败）
     */
    public static Optional<LocalDate> getBirthday(String idCard) {
        if (!isValid(idCard)) {
            return Optional.empty();
        }
        try {
            String trimmedId = idCard.trim();
            String birthdayStr;
            if (trimmedId.length() == 15) {
                birthdayStr = build15DigitBirthdayStr(trimmedId);
            } else {
                birthdayStr = trimmedId.substring(6, 14);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            return Optional.of(LocalDate.parse(birthdayStr, formatter));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * 从身份证号码提取性别
     *
     * @param idCard 身份证号码
     * @return 性别 {@link GenderEnums}
     */
    public static GenderEnums getGender(String idCard) {
        if (!isValid(idCard)) {
            return GenderEnums.UNKNOWN;
        }

        String trimmedId = idCard.trim();
        String genderDigit;

        // 15位身份证：最后一位；18位身份证：倒数第二位
        if (trimmedId.length() == 15) {
            genderDigit = trimmedId.substring(14, 15);
        } else {
            genderDigit = trimmedId.substring(16, 17);
        }

        int genderValue = Integer.parseInt(genderDigit);
        return genderValue % 2 == 1 ? GenderEnums.MALE : GenderEnums.FEMALE;
    }

    /**
     * 获取身份证所属省份
     *
     * @param idCard 身份证号码
     * @return 省份名称（Optional容器，空表示获取失败）
     */
    public static Optional<String> getProvince(String idCard) {
        if (!isValid(idCard)) {
            return Optional.empty();
        }

        String provinceCode = idCard.trim().substring(0, 2);
        return Optional.ofNullable(PROVINCE_CODES.get(provinceCode));
    }

    /**
     * 将15位身份证号码转换为18位
     *
     * @param idCard 15位身份证号码
     * @return 18位身份证号码（null表示转换失败）
     */
    public static String convert15To18(String idCard) {
        if (idCard == null) {
            return null;
        }

        String trimmedId = idCard.trim();
        if (trimmedId.length() != 15 || !isValid15DigitIdCard(trimmedId)) {
            return null;
        }

        // 构建17位前半部分（处理年份转换）
        String yyStr = trimmedId.substring(6, 8);
        int yy = Integer.parseInt(yyStr);
        int currentYear = LocalDate.now().getYear();
        int currentYearTwoDigit = currentYear % 100;
        int yyyy = yy <= currentYearTwoDigit ? 2000 + yy : 1900 + yy;

        String seventeenDigit = trimmedId.substring(0, 6) + yyyy + trimmedId.substring(8);

        // 计算校验码
        int mod = getMod(seventeenDigit);
        return seventeenDigit + CHECK_CODES[mod];
    }
}
