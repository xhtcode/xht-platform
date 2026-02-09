package com.xht.framework.core.enums;

import java.io.Serializable;

/**
 * 自定义查询
 *
 * @author xht
 **/
public interface XhtEnum<T extends Serializable> extends com.baomidou.mybatisplus.annotation.IEnum<T> {


    /**
     * 枚举数据库存储值
     */
    T getValue();

    /**
     * 枚举描述
     */
    default String getDesc() {
        throw new UnsupportedOperationException();
    }

}
