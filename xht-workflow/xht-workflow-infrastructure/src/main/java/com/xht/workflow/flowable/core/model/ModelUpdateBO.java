package com.xht.workflow.flowable.core.model;

import com.xht.workflow.flowable.core.BpmnBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 流程模型BO类
 * 用于封装流程模型的业务操作请求数据（新增/编辑）
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModelUpdateBO extends BpmnBO {

    /**
     * 模型ID
     */
    private String modelId;

    /**
     * 模型分类
     */
    private String category;

    /**
     * 模型元信息
     */
    private String metaInfo;
}
