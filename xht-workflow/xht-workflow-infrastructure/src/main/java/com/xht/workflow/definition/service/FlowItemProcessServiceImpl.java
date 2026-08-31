package com.xht.workflow.definition.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.code.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.definition.converter.FlowItemProcessConverter;
import com.xht.workflow.definition.dao.IFlowItemProcessDao;
import com.xht.workflow.definition.domain.form.FlowItemProcessForm;
import com.xht.workflow.definition.domain.query.FlowItemProcessPageQuery;
import com.xht.workflow.definition.domain.response.FlowItemProcessResponse;
import com.xht.workflow.definition.entity.FlowItemProcessEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述： 流程定义服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowItemProcessServiceImpl implements IFlowItemProcessService {

    private final IFlowItemProcessDao flowItemProcessDao;

    private final FlowItemProcessConverter flowItemProcessConverter;

    /**
     * 创建流程定义
     *
     * @param form 流程定义信息
     */
    @Override
    public void create(FlowItemProcessForm form) {
        FlowItemProcessEntity entity = flowItemProcessConverter.toEntity(form);
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
        flowItemProcessDao.updateFormRequest(id, form);
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
     * 分页查询流程定义
     *
     * @param query 流程定义查询参数
     * @return 流程定义分页信息
     */
    @Override
    public PageResponse<FlowItemProcessResponse> findPageList(FlowItemProcessPageQuery query) {
        Page<FlowItemProcessEntity> page = flowItemProcessDao.findPageList(PageTool.getPage(query), query);
        return flowItemProcessConverter.toResponse(page);
    }
}
