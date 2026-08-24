package com.xht.workflow.flowable.model.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 描述： 流程模型初始化参数建造者
 *
 * @author xht
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelInitBuilder extends ModelBuilder<ModelInitBO> {

    /**
     * 构建流程模型初始化参数建造者
     * @return 流程模型初始化参数建造者
     */
    public static ModelInitBuilder builder() {
        return new ModelInitBuilder();
    }

    /**
     * 构建流程模型初始化参数
     */
    @Override
    public ModelInitBO build() {
        validate();
        ModelInitBO modelInitBO = new ModelInitBO();
        modelInitBO.setModelName(modelName);
        modelInitBO.setModelKey(modelKey);
        modelInitBO.setCategory(category);
        modelInitBO.setMetaInfo(modelMetaInfo.getMetaInfoStr());
        return modelInitBO;
    }
}
