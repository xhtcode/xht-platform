package com.xht.workflow.flowable.core.model;

import com.xht.framework.exception.utils.ThrowUtils;
import com.xht.workflow.flowable.core.BpmnBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 描述：流程部署构建器
 *
 * @author xht
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelDeployBuilder implements BpmnBuilder<ModelDeployBO> {

    /**
     * 模型ID
     */
    protected String modelId;

    /**
     * 构建流程模型部署构建器
     * @return 流程模型部署构建器
     */
    public static ModelDeployBuilder builder() {
        return new ModelDeployBuilder();
    }

    /**
     * 设置模型ID
     * @param modelId 模型ID
     * @return 流程模型部署构建器
     */
    public ModelDeployBuilder modelId(String modelId) {
        this.modelId = modelId;
        return this;
    }

    /**
     * 构建流程模型初始化参数
     */
    @Override
    public ModelDeployBO build() {
        ThrowUtils.hasText(modelId, "模型ID 不能为空");
        ModelDeployBO modelDeployBO = new ModelDeployBO();
        modelDeployBO.setModelId(modelId);
        return modelDeployBO;
    }
}
