package com.xht.workflow.flowable.model.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 流程模型BO类
 * 用于封装流程模型的业务操作请求数据（新增/编辑）
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public class ModelInitBO extends ModelBO {

    protected ModelInitBO() {
    }


}
