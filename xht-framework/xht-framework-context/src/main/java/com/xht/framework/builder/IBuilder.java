package com.xht.framework.builder;

/**
 * 描述：公共的建造者接口
 *
 * @author xht
 **/
@FunctionalInterface
public interface IBuilder<T> {

    /**
     * 构建对象方法 建造者独有的方法
     *
     * @return 构建的对象
     */
    T build();

}
