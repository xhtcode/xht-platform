package com.xht.framework.core.utils;

import com.xht.framework.core.constant.StringConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * id生成器
 *
 * @author  xht
 */
@Slf4j
public final class IdUtils {

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID() {
        return randomUUID().replace(StringConstant.HORIZONTAL, StringConstant.EMPTY);
    }

    /**
     * 获取随机UUID（带横线）
     *
     * @return 随机UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

}
