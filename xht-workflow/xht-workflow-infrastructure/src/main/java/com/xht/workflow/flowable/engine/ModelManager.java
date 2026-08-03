package com.xht.workflow.flowable.engine;


import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.flowable.core.model.*;

/**
 * 描述： 流程模型管理
 *
 * @author xht
 **/
public interface ModelManager extends WorkFlowManager {

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
     * 检查流程模型标识是否存在
     *
     * @param modelKey       流程模型标识
     * @param excludeModelId 需要排除的流程模型ID
     * @return true：存在；false：不存在
     */
    boolean checkModelKeyExist(String modelKey, String excludeModelId);

    /**
     * 检查流程模型名称是否存在
     *
     * @param modelName      流程模型名称
     * @param excludeModelId 需要排除的流程模型ID
     * @return true：存在；false：不存在
     */
    boolean checkModelNameExist(String modelName, String excludeModelId);

    /**
     * 检查流程模型标识和名称是否存在
     *
     * @param modelKey       流程模型标识
     * @param modelName      流程模型名称
     * @param excludeModelId 需要排除的流程模型ID
     * @return true：存在；false：不存在
     */
    boolean checkModelKeyAndNameExist(String modelKey, String modelName, String excludeModelId);

    /**
     * 根据查询条件分页查询流程模型
     *
     * @param query 查询条件
     * @return 流程模型列表
     */
    PageResponse<ModelDTO> findPage(ModelPageQueryBO query);

    /**
     * 根据模型id查询 BPMN xml
     *
     * @param modelId 模型id
     * @return bpmn xml
     */
    String queryBpmnXmlById(String modelId);
}
