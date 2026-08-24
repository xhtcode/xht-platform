package com.xht.workflow.flowable.model.common;

import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.flowable.common.BpmnBuilder;
import com.xht.workflow.flowable.common.bo.MetaInfoBO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 描述： 流程模型初始化参数建造者
 *
 * @author xht
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ModelBuilder<T extends ModelBO> implements BpmnBuilder<T> {

    /**
     * 模型名称
     */
    protected String modelName;

    /**
     * 模型Key（流程定义标识）
     */
    protected String modelKey;

    /**
     * 模型分类
     */
    protected String category;

    /**
     * 模型元信息
     */
    protected MetaInfoBO modelMetaInfo;

    /**
     * 设置模型名称
     * @param modelName 模型名称
     * @return 构建者本身
     */
    public ModelBuilder<T> modelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    /**
     * 设置模型Key（流程定义标识）
     * @param modelKey 模型Key（流程定义标识）
     * @return 构建者本身
     */
    public ModelBuilder<T> modelKey(String modelKey) {
        this.modelKey = modelKey;
        return this;

    }

    /**
     * 设置模型分类
     * @param category 模型分类
     * @return 构建者本身
     */
    public ModelBuilder<T> category(String category) {
        this.category = category;
        return this;
    }

    /**
     * 设置模型元信息
     * @param modelMetaInfo 模型元信息
     * @return 构建者本身
     */
    public ModelBuilder<T> modelMetaInfo(MetaInfoBO modelMetaInfo) {
        this.modelMetaInfo = modelMetaInfo;
        return this;
    }

    /**
     * 校验
     */
    protected void validate() {
        ThrowUtils.hasText(modelName, "模型名称不能为空");
        ThrowUtils.hasText(modelKey, "模型Key不能为空");
        ThrowUtils.hasText(category, "模型分类不能为空");
        if (Objects.isNull(modelMetaInfo)) {
            this.modelMetaInfo = MetaInfoBO.builder();
        }
    }

}
