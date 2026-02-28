package com.xht.framework.core.utils;


import com.xht.framework.core.constant.CharacterConstant;
import com.xht.framework.core.constant.StringConstant;

/**
 * 描述 ：{@link StringUtils}工具类扩展
 *
 * @author xht
 **/
@SuppressWarnings("unused")
public final class StringUtils extends org.springframework.util.StringUtils {

    private static final String regex = "[\\u4e00-\\u9fff]+"; // 匹配一个或多个汉字

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 为空就是true
     */
    public static boolean isEmpty(String str) {
        return !hasText(str);
    }

    /**
     * 转换成字符串
     */
    public static String str(Object obj) {
        return str(obj, null);
    }

    /**
     * 转换成字符串
     */
    public static String str(Object obj, String defaultValue) {
        return (obj == null) ? defaultValue : obj.toString();
    }


    /**
     * 替换中文
     *
     * @param str        源字符串
     * @param replaceStr 替换的字符变成的什么
     * @return 替换后的字符串
     */
    public static String replaceChinese(String str, String replaceStr) {
        if (hasText(str)) {
            return str.replaceAll(regex, replaceStr);
        }
        return str;
    }


    /**
     * 当字符串空的时候，返回默认值,不为空返回当前值
     */
    public static String emptyToDefault(String value) {
        return emptyToDefault(value, StringConstant.EMPTY);
    }

    /**
     * 当字符串空的时候，返回默认值
     */
    public static String emptyToDefault(String value, String defaultValue) {
        if (hasText(value)) {
            return value;
        }
        return defaultValue;
    }

    /**
     * 字符串驼峰转下划线格式
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String camelToUnderline(String param) {
        if (isEmpty(param)) {
            return StringConstant.EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(StringConstant.UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 字符串下划线转驼峰格式
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String underlineToCamel(String param) {
        if (isEmpty(param)) {
            return StringConstant.EMPTY;
        }
        String temp = param.toLowerCase();
        int len = temp.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = temp.charAt(i);
            if (c == CharacterConstant.UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(temp.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    /**
     * 比较两个字符串（大小写敏感）。
     *
     * <pre>
     * equals(null, null)   = true
     * equals(null, &quot;abc&quot;)  = false
     * equals(&quot;abc&quot;, null)  = false
     * equals(&quot;abc&quot;, &quot;abc&quot;) = true
     * equals(&quot;abc&quot;, &quot;ABC&quot;) = false
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是{@code null}，则返回{@code true}
     */
    public static boolean equals(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, false);
    }

    /**
     * 比较两个字符串（大小写不敏感）。
     *
     * <pre>
     * equalsIgnoreCase(null, null)   = true
     * equalsIgnoreCase(null, &quot;abc&quot;)  = false
     * equalsIgnoreCase(&quot;abc&quot;, null)  = false
     * equalsIgnoreCase(&quot;abc&quot;, &quot;abc&quot;) = true
     * equalsIgnoreCase(&quot;abc&quot;, &quot;ABC&quot;) = true
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是{@code null}，则返回{@code true}
     */
    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, true);
    }

    /**
     * 比较两个字符串是否相等，规则如下
     * <ul>
     *     <li>str1和str2都为{@code null}</li>
     *     <li>忽略大小写使用{@link String#equalsIgnoreCase(String)}判断相等</li>
     *     <li>不忽略大小写使用{@link String#contentEquals(CharSequence)}判断相等</li>
     * </ul>
     *
     * @param str1       要比较的字符串1
     * @param str2       要比较的字符串2
     * @param ignoreCase 是否忽略大小写
     * @return 如果两个字符串相同，或者都是{@code null}，则返回{@code true}
     * @since 3.2.0
     */
    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (null == str1) {
            // 只有两个都为null才判断相等
            return str2 == null;
        }
        if (null == str2) {
            // 字符串2空，字符串1非空，直接false
            return false;
        }

        if (ignoreCase) {
            return str1.toString().equalsIgnoreCase(str2.toString());
        } else {
            return str1.toString().contentEquals(str2);
        }
    }

}
