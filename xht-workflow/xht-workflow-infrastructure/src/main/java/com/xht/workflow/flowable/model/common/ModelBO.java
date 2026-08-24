package com.xht.workflow.flowable.model.common;

import com.xht.workflow.flowable.common.bo.BpmnBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 描述： 流程模型业务对象
 *
 * @author xht
 **/
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModelBO extends BpmnBO {

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 模型Key（流程定义标识）
     */
    private String modelKey;

    /**
     * 模型分类
     */
    private String category;

    /**
     * 模型元信息
     */
    private String metaInfo;

}
