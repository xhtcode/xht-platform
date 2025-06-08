package com.xht.framework.core.utils;

/**
 * 描述 ：{@link ObjectUtils}工具类扩展
 *
 * @author xht
 **/
public class ObjectUtils extends org.springframework.util.ObjectUtils {

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }
}
