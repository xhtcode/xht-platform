package com.xht.workflow.flowable.core.model;

import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.flowable.core.BpmnBuilder;
import com.xht.workflow.flowable.core.MetaInfoBO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 流程模型BO类
 * 用于封装流程模型的业务操作请求数据（新增/编辑）
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelUpdateBuilder implements BpmnBuilder<ModelUpdateBO> {

    /**
     * 模型ID
     */
    protected String modelId;

    /**
     * 模型分类
     */
    protected String category;

    /**
     * 模型元信息
     */
    protected MetaInfoBO modelMetaInfo;


    /**
     * 构建流程模型更新参数
     * @return 流程模型更新参数
     */
    public static ModelUpdateBuilder builder() {
        return new ModelUpdateBuilder();
    }

    /**
     * 设置模型ID
     * @param modelId 模型ID
     * @return 流程模型更新参数构建器
     */
    public ModelUpdateBuilder modelId(String modelId) {
        this.modelId = modelId;
        return this;
    }

    /**
     * 设置模型分类
     * @param category 模型分类
     * @return 构建者本身
     */
    public ModelUpdateBuilder category(String category) {
        this.category = category;
        return this;
    }

    /**
     * 设置模型元信息
     * @param modelMetaInfo 模型元信息
     * @return 构建者本身
     */
    public ModelUpdateBuilder modelMetaInfo(MetaInfoBO modelMetaInfo) {
        this.modelMetaInfo = modelMetaInfo;
        return this;
    }

    /**
     * 构建流程模型初始化参数
     */
    @Override
    public ModelUpdateBO build() {
        ThrowUtils.hasText(category, "模型分类不能为空");
        if (Objects.isNull(modelMetaInfo)) {
            this.modelMetaInfo = MetaInfoBO.builder();
        }
        ModelUpdateBO modelUpdateBO = new ModelUpdateBO();
        modelUpdateBO.setModelId(modelId);
        modelUpdateBO.setCategory(category);
        modelUpdateBO.setMetaInfo(modelMetaInfo.getMetaInfoStr());
        return modelUpdateBO;
    }
}
