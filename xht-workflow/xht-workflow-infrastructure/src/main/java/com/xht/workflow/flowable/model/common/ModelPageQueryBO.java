package com.xht.workflow.flowable.model.common;

import com.xht.workflow.flowable.common.bo.BpmnPageQueryBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 描述： 模型查询参数
 *
 * @author xht
 **/
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModelPageQueryBO extends BpmnPageQueryBO {

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

}
