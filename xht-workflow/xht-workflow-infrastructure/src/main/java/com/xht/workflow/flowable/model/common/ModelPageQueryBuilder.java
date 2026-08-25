package com.xht.workflow.flowable.model.common;

import com.xht.workflow.flowable.common.bulder.BpmnPageQueryBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 描述： 模型查询参数
 *
 * @author xht
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelPageQueryBuilder extends BpmnPageQueryBuilder<ModelPageQueryBO> {

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
     * 构建模型查询参数
     *
     * @return 模型查询参数
     */
    public static ModelPageQueryBuilder builder() {
        return new ModelPageQueryBuilder();
    }

    /**
     * 设置模型名称
     * @param modelName 模型名称
     * @return 构建者本身
     */
    public ModelPageQueryBuilder modelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    /**
     * 设置模型Key（流程定义标识）
     * @param modelKey 模型Key（流程定义标识）
     * @return 构建者本身
     */
    public ModelPageQueryBuilder modelKey(String modelKey) {
        this.modelKey = modelKey;
        return this;

    }

    /**
     * 设置模型分类
     * @param category 模型分类
     * @return 构建者本身
     */
    public ModelPageQueryBuilder category(String category) {
        this.category = category;
        return this;
    }


    /**
     * 填充查询参数
     */
    @Override
    protected ModelPageQueryBO createQueryData() {
        ModelPageQueryBO modelQuery = new ModelPageQueryBO();
        modelQuery.setModelName(modelName);
        modelQuery.setModelKey(modelKey);
        modelQuery.setCategory(category);
        return modelQuery;
    }

}
