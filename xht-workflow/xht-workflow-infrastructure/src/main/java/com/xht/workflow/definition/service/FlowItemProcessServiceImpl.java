package com.xht.workflow.definition.service;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.code.BusinessErrorCode;
import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.definition.converter.FlowItemProcessConverter;
import com.xht.workflow.definition.dao.FlowItemDefDao;
import com.xht.workflow.definition.dao.FlowItemProcessDao;
import com.xht.workflow.definition.domain.form.FlowItemProcessForm;
import com.xht.workflow.definition.domain.query.FlowItemProcessPageQuery;
import com.xht.workflow.definition.domain.response.FlowItemProcessResponse;
import com.xht.workflow.definition.entity.FlowItemDefEntity;
import com.xht.workflow.definition.entity.FlowItemProcessEntity;
import com.xht.workflow.definition.enums.FlowDefinitionTypeEnum;
import com.xht.workflow.common.domain.enums.ProcStartTypeEnum;
import com.xht.workflow.flowable.definition.ProcessDefinitionManager;
import com.xht.workflow.flowable.definition.common.ProcessDefinitionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 描述： 流程定义服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowItemProcessServiceImpl implements IFlowItemProcessService {

    private final FlowItemDefDao flowItemDefDao;

    private final FlowItemProcessDao flowItemProcessDao;

    private final FlowItemProcessConverter flowItemProcessConverter;

    private final ProcessDefinitionManager processDefinitionManager;

    /**
     * 创建流程定义
     *
     * @param form 流程定义信息
     */
    @Override
    public void create(FlowItemProcessForm form) {
        checkItemDef(form.getItemDefId());
        FlowItemProcessEntity entity = fillProcessDefinition(form);
        flowItemProcessDao.saveTransactional(entity);
    }

    /**
     * 删除流程定义
     *
     * @param id 流程定义ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        flowItemProcessDao.removeById(id);
    }

    /**
     * 修改流程定义
     *
     * @param id   流程定义ID
     * @param form 流程定义信息
     */
    @Override
    public void updateById(Long id, FlowItemProcessForm form) {
        ThrowUtils.notNull(id);
        checkItemDef(form.getItemDefId());
        FlowItemProcessEntity entity = fillProcessDefinition(form);
        flowItemProcessDao.updateFormRequest(id, entity);
    }

    /**
     * 表单转实体并根据流程启动方式查询填充流程定义数据
     *
     * @param form 表单对象
     * @return 填充后的实体对象
     */
    private FlowItemProcessEntity fillProcessDefinition(FlowItemProcessForm form) {
        FlowItemProcessEntity entity = flowItemProcessConverter.toEntity(form);
        ProcessDefinitionDTO processDefinition;
        if (Objects.equals(entity.getProcStartType(), ProcStartTypeEnum.ID)) {
            processDefinition = processDefinitionManager.findByProcessDefId(entity.getProcDefId());
        } else if (Objects.equals(entity.getProcStartType(), ProcStartTypeEnum.KEY)) {
            processDefinition = processDefinitionManager.findByProcessDefKey(entity.getProcDefKey());
        } else {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "流程启动方式不合法");
        }
        ThrowUtils.throwIf(Objects.isNull(processDefinition), BusinessErrorCode.DATA_NOT_EXIST, "流程定义不存在");
        entity.setProcDefId(processDefinition.getProcessDefId());
        entity.setProcDefKey(processDefinition.getProcessDefKey());
        entity.setProcDefName(processDefinition.getProcessDefName());
        entity.setProcDefVersion(processDefinition.getProcessDefVersion());
        entity.setDeploymentId(processDefinition.getDeploymentId());
        return entity;
    }

    /**
     * 校验事项定义是否存在且为事项类型
     *
     * @param itemDefId 事项定义ID
     */
    private void checkItemDef(Long itemDefId) {
        FlowItemDefEntity itemDef = flowItemDefDao.findOptionalById(itemDefId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST, "事项定义不存在"));
        ThrowUtils.throwIf(Objects.equals(itemDef.getItemType(), FlowDefinitionTypeEnum.CATEGORY),
                BusinessErrorCode.PARAM_ERROR, "事项定义是分类，禁止关联流程");
    }

    /**
     * 获取流程定义详情
     *
     * @param id 流程定义ID
     * @return 流程定义详情
     */
    @Override
    public FlowItemProcessResponse findById(Long id) {
        return flowItemProcessConverter.toResponse(flowItemProcessDao.findOptionalById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST)));
    }

    /**
     * 获取流程定义列表
     *
     * @param query 流程定义查询参数
     * @return 流程定义列表
     */
    @Override
    public List<FlowItemProcessResponse> findList(FlowItemProcessPageQuery query) {
        return flowItemProcessConverter.toResponse(flowItemProcessDao.findList(query));
    }
}
