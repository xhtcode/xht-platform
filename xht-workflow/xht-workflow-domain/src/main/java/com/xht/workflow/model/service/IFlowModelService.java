package com.xht.workflow.model.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.model.domain.form.FlowModelDesignForm;
import com.xht.workflow.model.domain.form.FlowModelInitForm;
import com.xht.workflow.model.domain.form.FlowModelUpdateForm;
import com.xht.workflow.model.domain.query.FlowModelPageQuery;
import com.xht.workflow.model.domain.response.FlowModelResponse;

/**
 * 描述： 流程模型服务接口
 *
 * @author xht
 **/
public interface IFlowModelService {

    /**
     * 初始化流程模型
     *
     * @param modelInitForm 流程模型初始化信息
     */
    void modelInit(FlowModelInitForm modelInitForm);

    /**
     * 删除流程模型
     *
     * @param modelId 流程模型ID
     */
    void removeModelById(String modelId);

    /**
     * 更新流程模型
     *
     * @param modelUpdateForm 流程模型更新信息
     */
    void updateModel(FlowModelUpdateForm modelUpdateForm);

    /**
     * 设计流程模型
     *
     * @param modelDesignForm 流程模型设计信息
     */
    void modelDesign(FlowModelDesignForm modelDesignForm);

    /**
     * 部署模型
     * @param modelId 流程模型ID
     */
    void deployModel(String modelId);

    /**
     * 获取流程模型详情
     *
     * @param modelId 流程模型ID
     * @return 流程模型详情
     */
    FlowModelResponse findModelById(String modelId);

    /**
     * 分页查询流程模型
     *
     * @param query 流程模型查询参数
     * @return 分页结果
     */
    PageResponse<FlowModelResponse> findModelByPage(FlowModelPageQuery query);

    /**
     * 根据模型id查询 BPMN xml
     *
     * @param modelId 模型id
     * @return bpmn xml
     */
    String queryBpmnXmlById(String modelId);
}
