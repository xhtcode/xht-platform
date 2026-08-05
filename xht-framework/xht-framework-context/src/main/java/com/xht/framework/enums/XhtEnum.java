package com.xht.framework.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

/**
 * 描述： 统一枚举接口
 *
 * @author xht
 **/
public interface XhtEnum<T extends Serializable> extends IEnum<T> {

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
