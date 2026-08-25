package com.xht.workflow.flowable.model;


import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.flowable.common.bo.BpmnPageQueryBO;
import com.xht.workflow.flowable.model.common.*;

/**
 * 描述： 流程模型管理
 *
 * @author xht
 **/
public interface ModelManager {

    /**
     * 初始化模型
     *
     * @param modelInitBO 模型初始化参数
     * @return 模型id
     */
    ModelDTO initModel(ModelInitBO modelInitBO);

    /**
     * 根据模型id删除
     *
     * @param modelId 模型id
     */
    void removeByModelId(String modelId);

    /**
     * 更新模型
     *
     * @param modelUpdateBO 模型初始化参数
     */
    void updateModel(ModelUpdateBO modelUpdateBO);

    /**
     * 更新模型设计
     *
     * @param modelDesignBO 模型设计参数
     * @return 模型id
     */
    ModelDTO updateModelDesign(ModelDesignBO modelDesignBO);

    /**
     * 部署模型
     *
     * @param modelDeployBO 部署参数
     */
    void deployModel(ModelDeployBO modelDeployBO);

    /**
     * 根据模型id获取模型
     *
     * @param modelId 模型id
     * @return 模型
     */
    ModelDTO findByModelId(String modelId);

    /**
     * 根据查询条件分页查询流程模型
     *
     * @param query 查询条件
     * @return 流程模型列表
     */
    PageResponse<ModelDTO> findPage(ModelPageQueryBO query);

    /**
     * 根据模型id分页查询流程模型历史
     *
     * @param modelId 模型id
     * @param query   查询条件
     * @return 流程模型列表
     */
    PageResponse<ModelDTO> historyPage(String modelId, BpmnPageQueryBO query);

    /**
     * 根据模型id查询 BPMN xml
     *
     * @param modelId 模型id
     * @return bpmn xml
     */
    String queryBpmnXmlById(String modelId);

}
