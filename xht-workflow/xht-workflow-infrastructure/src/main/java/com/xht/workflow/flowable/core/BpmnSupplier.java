package com.xht.workflow.flowable.core;

/**
 * 描述：flowable 供应商接口
 *
 * @author xht
 **/
public interface BpmnSupplier<T, E> {

    /**
     * 获取结果
     *
     * @param e 参数
     * @return 结果
     */
    T get(E e);

}
