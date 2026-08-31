package com.xht.workflow.model.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.code.BusinessErrorCode;
import com.xht.framework.mybatis.utils.SortTool;
import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.common.domain.query.WorkFlowPageQuery;
import com.xht.workflow.definition.dao.IFlowItemDefDao;
import com.xht.workflow.definition.entity.FlowItemDefEntity;
import com.xht.workflow.flowable.common.bo.MetaInfoBO;
import com.xht.workflow.flowable.model.ModelManager;
import com.xht.workflow.flowable.model.common.*;
import com.xht.workflow.model.converter.FlowModelConverter;
import com.xht.workflow.model.domain.form.FlowModelDesignForm;
import com.xht.workflow.model.domain.form.FlowModelInitForm;
import com.xht.workflow.model.domain.form.FlowModelUpdateForm;
import com.xht.workflow.model.domain.query.FlowModelPageQuery;
import com.xht.workflow.model.domain.response.FlowModelResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 描述： 流程模型服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowModelServiceImpl implements IFlowModelService {

    private final ModelManager modelManager;

    private final IFlowItemDefDao flowCategoryDao;

    private final FlowModelConverter flowModelConverter;

    /**
     * 初始化流程模型
     *
     * @param modelInitForm 流程模型初始化信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modelInit(FlowModelInitForm modelInitForm) {
        FlowItemDefEntity category = flowCategoryDao.findById(modelInitForm.getCategoryId());
        ThrowUtils.throwIf(Objects.isNull(category), BusinessErrorCode.DATA_NOT_EXIST, "流程定义不存在");
        ModelInitBO build = ModelInitBuilder.builder()
                .category(category.getItemName())
                .modelName(modelInitForm.getModelName())
                .modelKey(modelInitForm.getModelKey())
                .modelMetaInfo(MetaInfoBO.builder().put("categoryId", category.getId()).putAll(modelInitForm.getMetaInfo()))
                .build();
        modelManager.initModel(build);
    }

    /**
     * 删除流程模型
     *
     * @param modelId 流程模型ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeModelById(String modelId) {
        modelManager.removeByModelId(modelId);
    }

    /**
     * 更新流程模型
     *
     * @param modelUpdateForm 流程模型更新信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateModel(FlowModelUpdateForm modelUpdateForm) {
        FlowItemDefEntity category = flowCategoryDao.findById(modelUpdateForm.getCategoryId());
        ThrowUtils.throwIf(Objects.isNull(category), BusinessErrorCode.DATA_NOT_EXIST, "流程定义不存在");
        ModelUpdateBO modelUpdateBO = ModelUpdateBuilder.builder()
                .category(category.getItemName())
                .modelId(modelUpdateForm.getModelId())
                .modelMetaInfo(MetaInfoBO.builder().put("categoryId", category.getId()).putAll(modelUpdateForm.getMetaInfo()))
                .build();
        modelManager.updateModel(modelUpdateBO);
    }

    /**
     * 设计流程模型
     *
     * @param modelDesignForm 流程模型设计信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modelDesign(FlowModelDesignForm modelDesignForm) {
        ModelDesignBO modelDesignBO = ModelDesignBuilder.builder()
                .modelId(modelDesignForm.getModelId())
                .bpmnXml(modelDesignForm.getBpmnXml())
                .newVersion(modelDesignForm.getNewVersion())
                .build();
        modelManager.updateModelDesign(modelDesignBO);
    }

    /**
     * 部署模型
     *
     * @param modelId 流程模型ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deployModel(String modelId) {
        modelManager.deployModel(ModelDeployBuilder.builder().modelId(modelId).build());
    }

    /**
     * 获取流程模型详情
     *
     * @param modelId 流程模型ID
     * @return 流程模型详情
     */
    @Override
    public FlowModelResponse findModelById(String modelId) {
        ModelDTO byModelId = modelManager.findByModelId(modelId);
        return flowModelConverter.toResponse(byModelId);
    }

    /**
     * 分页查询流程模型
     *
     * @param query 流程模型查询参数
     * @return 分页结果
     */
    @Override
    public PageResponse<FlowModelResponse> findModelByPage(FlowModelPageQuery query) {
        // @formatter:off
        ModelPageQueryBO modelPageQueryBO = ModelPageQueryBuilder.builder()
                .category(query.getCategory())
                .modelName(query.getModelName())
                .modelKey(query.getModelKey())
                .current(query.getCurrent())
                .size(query.getSize())
                .asc(SortTool.getAscSort(query))
                .desc(SortTool.getDescSort(query))
                .build();
        // @formatter:on
        PageResponse<ModelDTO> page = modelManager.findPage(modelPageQueryBO);
        return flowModelConverter.toResponse(page);
    }

    /**
     * 分页查询流程模型历史版本
     *
     * @param modelId 流程模型ID
     * @param query   流程模型查询参数
     */
    @Override
    public PageResponse<FlowModelResponse> findHistoryModelByPage(String modelId, WorkFlowPageQuery query) {
        // @formatter:off
        ModelPageQueryBO modelPageQueryBO = ModelPageQueryBuilder.builder()
                .size(query.getSize())
                .asc(SortTool.getAscSort(query))
                .desc(SortTool.getDescSort(query))
                .build();
        // @formatter:on
        PageResponse<ModelDTO> page = modelManager.historyPage(modelId, modelPageQueryBO);
        return flowModelConverter.toResponse(page);
    }


    /**
     * 根据模型id查询 BPMN xml
     *
     * @param modelId 模型id
     * @return bpmn xml
     */
    @Override
    public String queryBpmnXmlById(String modelId) {
        ModelDTO modelDTO = modelManager.findByModelId(modelId);
        ThrowUtils.throwIf(Objects.isNull(modelDTO), BusinessErrorCode.DATA_NOT_EXIST, "模型不存在");
        return modelManager.queryBpmnXmlById(modelId);
    }

}
