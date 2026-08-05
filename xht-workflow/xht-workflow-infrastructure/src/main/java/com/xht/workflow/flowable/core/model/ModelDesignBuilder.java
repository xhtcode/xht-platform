package com.xht.workflow.flowable.core.model;

import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.flowable.core.BpmnBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 描述： 模型设计构建器
 *
 * @author xht
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelDesignBuilder implements BpmnBuilder<ModelDesignBO> {

    /**
     * 构建模型设计构建器
     * @return 模型设计构建器
     */
    public static ModelDesignBuilder builder() {
        return new ModelDesignBuilder();
    }

    /**
     * 模型ID
     */
    protected String modelId;

    /**
     * BPMN xml
     */
    protected String bpmnXml;

    /**
     * 是否新版本
     */
    protected Boolean newVersion;

    /**
     * 设置模型ID
     * @param modelId 模型ID
     * @return 流程模型更新参数构建器
     */
    public ModelDesignBuilder modelId(String modelId) {
        this.modelId = modelId;
        return this;
    }

    /**
     * 设置BPMN xml
     * @param bpmnXml BPMN xml
     * @return 流程模型更新参数构建器
     */
    public ModelDesignBuilder bpmnXml(String bpmnXml) {
        this.bpmnXml = bpmnXml;
        return this;
    }

    /**
     * 设置是否新版本
     * @param newVersion 是否新版本
     * @return 流程模型更新参数构建器
     */
    public ModelDesignBuilder newVersion(Boolean newVersion) {
        this.newVersion = newVersion;
        return this;
    }

    /**
     * 构建流程模型初始化参数
     */
    @Override
    public ModelDesignBO build() {
        ThrowUtils.hasText(modelId, "模型ID 不能为空");
        ThrowUtils.hasText(bpmnXml, "bpmnXml 不能为空");
        ModelDesignBO modelDesignBO = new ModelDesignBO();
        modelDesignBO.setBpmnXml(bpmnXml);
        modelDesignBO.setNewVersion(newVersion);
        return modelDesignBO;
    }

}
