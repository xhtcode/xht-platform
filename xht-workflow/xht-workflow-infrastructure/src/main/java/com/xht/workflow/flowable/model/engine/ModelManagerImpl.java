package com.xht.workflow.flowable.model.engine;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.utils.Base64Utils;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.common.constant.BpmnConstant;
import com.xht.workflow.common.exception.WorkFlowException;
import com.xht.workflow.flowable.common.bo.BpmnOrder;
import com.xht.workflow.flowable.model.ModelManager;
import com.xht.workflow.flowable.model.common.*;
import com.xht.workflow.flowable.model.converter.FlowableModelConverter;
import com.xht.workflow.flowable.utils.BpmnUtils;
import com.xht.workflow.flowable.utils.FlowableQueryUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.ModelQueryProperty;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 描述： 流程模型管理
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class ModelManagerImpl implements ModelManager {

    /**
     * 流程仓库服务
     * 负责流程定义部署、删除、查询；读取BPMN模型、流程图资源
     */
    private final RepositoryService repositoryService;

    private final FlowableModelConverter modelConverter;

    /**
     * 初始化模型
     *
     * @param modelInitBO 模型初始化参数
     * @return 模型id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ModelDTO initModel(ModelInitBO modelInitBO) {
        ThrowUtils.notNull(modelInitBO, "模型初始化参数不能为空");
        long count = repositoryService.createModelQuery()
                .modelKey(modelInitBO.getModelKey())
                .modelName(modelInitBO.getModelName())
                .count();
        ThrowUtils.throwIf(count > 0, "流程模型标识或名称已存在！");
        Model model = repositoryService.newModel();
        model.setName(modelInitBO.getModelName());
        model.setKey(modelInitBO.getModelKey());
        model.setCategory(modelInitBO.getCategory());
        model.setMetaInfo(modelInitBO.getMetaInfo());
        model.setTenantId(null);
        repositoryService.saveModel(model);
        return modelConverter.convert(model);
    }

    /**
     * 根据模型id删除
     *
     * @param modelId 模型id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByModelId(String modelId) {
        repositoryService.deleteModel(modelId);
    }

    /**
     * 更新模型
     *
     * @param modelUpdateBO 模型初始化参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateModel(ModelUpdateBO modelUpdateBO) {
        Model model = repositoryService.getModel(modelUpdateBO.getModelId());
        if (null == model) {
            throw new WorkFlowException("流程模型不存在！");
        } else {
            model.setCategory(modelUpdateBO.getCategory());
            model.setMetaInfo(modelUpdateBO.getMetaInfo());
            repositoryService.saveModel(model);
        }
    }

    /**
     * 更新模型设计
     *
     * @param modelDesignBO 模型设计参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ModelDTO updateModelDesign(ModelDesignBO modelDesignBO) {
        Model model = repositoryService.getModel(modelDesignBO.getModelId());
        ThrowUtils.notNull(model, "流程模型不存在！");
        BpmnModel bpmnModel = BpmnUtils.getBpmnModel(modelDesignBO.getBpmnXml());
        ThrowUtils.notNull(model, "获取模型设计失败！");
        String processId = bpmnModel.getMainProcess().getId();
        String processName = bpmnModel.getMainProcess().getName();
        if (!StringUtils.equals(model.getKey(), processId)) {
            throw new WorkFlowException("BPMN.xml错误， 流程模型标识与主流程id不一致！");
        }
        if (!StringUtils.equals(model.getName(), processName)) {
            throw new WorkFlowException("BPMN.xml错误， 流程模型名称与主流程名称不一致！");
        }
        Model newModel;
        if (modelDesignBO.getNewVersion()) {
            Integer latestVersion = repositoryService
                    .createModelQuery()
                    .modelKey(processId)
                    .latestVersion()
                    .singleResult()
                    .getVersion();
            newModel = repositoryService.newModel();
            newModel.setKey(processId);
            newModel.setName(processName);
            newModel.setCategory(model.getCategory());
            newModel.setMetaInfo(model.getMetaInfo());
            newModel.setVersion(latestVersion + 1);
        } else {
            newModel = model;
            model.setName(processName);
        }
        repositoryService.saveModel(newModel);
        repositoryService.addModelEditorSource(newModel.getId(), BpmnUtils.getBpmnXml(bpmnModel));
        return modelConverter.convert(newModel);
    }

    /**
     * 部署模型
     *
     * @param modelDeployBO 部署参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deployModel(ModelDeployBO modelDeployBO) {
        Model model = repositoryService.getModel(modelDeployBO.getModelId());
        if (null == model) {
            throw new WorkFlowException("流程模型不存在！");
        }
        String bpmnXml = queryBpmnXmlById(modelDeployBO.getModelId());
        BpmnModel bpmnModel = BpmnUtils.getBpmnModel(bpmnXml);
        String processName = model.getName() + BpmnConstant.BPMN_FILE_SUFFIX;
        Deployment deployment = repositoryService.createDeployment()
                .name(model.getName())
                .key(model.getKey())
                .category(model.getCategory())
                .tenantId(model.getTenantId())
                .addBpmnModel(processName, bpmnModel)
                .deploy();
        ProcessDefinition procDef = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        repositoryService.setProcessDefinitionCategory(procDef.getId(), model.getCategory());
        model.setDeploymentId(deployment.getId());
        repositoryService.saveModel(model);
    }

    /**
     * 根据模型id获取模型
     *
     * @param modelId 模型id
     * @return 模型
     */
    @Override
    public ModelDTO findByModelId(String modelId) {
        Assert.hasText(modelId, "模型id不能为空");
        Model model = repositoryService.getModel(modelId);
        return modelConverter.convert(model);
    }

    /**
     * 根据查询条件分页查询流程模型
     *
     * @param query 查询条件
     * @return 流程模型列表
     */
    @Override
    public PageResponse<ModelDTO> findPage(ModelPageQueryBO query) {
        ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion();
        return basicQuery(modelQuery, query);
    }

    /**
     * 根据模型id分页查询流程模型历史
     *
     * @param modelId 模型id
     * @param query   查询条件
     * @return 流程模型列表
     */
    @Override
    public PageResponse<ModelDTO> historyPage(String modelId, ModelPageQueryBO query) {
        Assert.hasText(modelId, "模型id不能为空");
        ModelQuery modelQuery = repositoryService.createModelQuery().modelId(modelId);
        return basicQuery(modelQuery, query);
    }

    /**
     * 基本查询
     *
     * @param modelQuery 模型查询对象
     * @param query      查询条件
     * @return 流程模型列表
     */
    public PageResponse<ModelDTO> basicQuery(ModelQuery modelQuery, ModelPageQueryBO query) {
        if (StringUtils.hasText(query.getModelName())) {
            modelQuery.modelNameLike(FlowableQueryUtils.appendLikePrefix(query.getModelName()));
        }
        if (StringUtils.hasText(query.getModelKey())) {
            modelQuery.modelKey(query.getModelKey());
        }
        if (StringUtils.hasText(query.getCategory())) {
            modelQuery.modelCategoryLike(FlowableQueryUtils.appendLikePrefix(query.getCategory()));
        }
        List<BpmnOrder> orders = query.getOrders();
        if (!CollectionUtils.isEmpty(orders)) {
            for (BpmnOrder order : orders) {
                ThrowUtils.notNull(order, "排序参数[orders]不能为空");
                String name = order.getName();
                BpmnOrder.BpmnOrderType orderType = Objects.isNull(order.getOrderType()) ? BpmnOrder.BpmnOrderType.ASC : order.getOrderType();
                ThrowUtils.hasText(name, "排序字段不能为空");
                ModelQueryProperty sortProperty = switch (name) {
                    case "modelName" -> ModelQueryProperty.MODEL_NAME;
                    case "modelKey" -> ModelQueryProperty.MODEL_KEY;
                    case "lastUpdateTime" -> ModelQueryProperty.MODEL_LAST_UPDATE_TIME;
                    default -> ModelQueryProperty.MODEL_CREATE_TIME;
                };
                // 映射前端字段 -> Flowable内置属性
                modelQuery.orderBy(sortProperty);
                if (orderType.equals(BpmnOrder.BpmnOrderType.DESC)) {
                    modelQuery.desc();
                } else {
                    modelQuery.asc();
                }
            }
        }
        return FlowableQueryUtils.findPage(modelQuery, query, modelConverter::convert);
    }

    /**
     * 根据模型id查询bpmn xml
     *
     * @param modelId 模型id
     * @return bpmn xml
     */
    @Override
    public String queryBpmnXmlById(String modelId) {
        ThrowUtils.hasText(modelId, "模型id不能为空");
        byte[] bpmnBytes = this.repositoryService.getModelEditorSource(modelId);
        return Base64Utils.encodeToString(bpmnBytes);
    }

}
