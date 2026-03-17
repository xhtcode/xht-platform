package com.xht.framework.common.enums;

import java.io.Serializable;

/**
 * 自定义查询
 *
 * @author xht
 **/
public interface XhtEnum<T extends Serializable>  {


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
