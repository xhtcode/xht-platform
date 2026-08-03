package com.xht.workflow.flowable.core;

/**
 * 描述： 流程模型初始化参数建造者
 *
 * @author xht
 **/
public interface BpmnBuilder<T extends  BpmnBO> {

    /**
     * 构建流程模型初始化参数
     */
    T build();

}
