package com.xht.workflow.definition.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.code.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.definition.converter.FlowFormConverter;
import com.xht.workflow.definition.dao.FlowFormDao;
import com.xht.workflow.definition.domain.form.FlowFormForm;
import com.xht.workflow.definition.domain.query.FlowFormPageQuery;
import com.xht.workflow.definition.domain.response.FlowFormResponse;
import com.xht.workflow.definition.entity.FlowFormEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述： 流程表单服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowFormServiceImpl implements IFlowFormService {

    private final FlowFormDao flowFormDao;

    private final FlowFormConverter flowFormConverter;

    /**
     * 创建流程表单
     *
     * @param form 流程表单信息
     */
    @Override
    public void create(FlowFormForm form) {
        FlowFormEntity entity = flowFormConverter.toEntity(form);
        flowFormDao.saveTransactional(entity);
    }

    /**
     * 删除流程表单
     *
     * @param id 流程表单ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        flowFormDao.removeById(id);
    }

    /**
     * 修改流程表单
     *
     * @param id   流程表单ID
     * @param form 流程表单信息
     */
    @Override
    public void updateById(Long id, FlowFormForm form) {
        ThrowUtils.notNull(id);
        flowFormDao.updateFormRequest(id, form);
    }

    /**
     * 获取流程表单详情
     *
     * @param id 流程表单ID
     * @return 流程表单详情
     */
    @Override
    public FlowFormResponse findById(Long id) {
        return flowFormConverter.toResponse(flowFormDao.findOptionalById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST)));
    }

    /**
     * 分页查询流程表单
     *
     * @param query 流程表单查询参数
     * @return 流程表单分页信息
     */
    @Override
    public PageResponse<FlowFormResponse> findPageList(FlowFormPageQuery query) {
        Page<FlowFormEntity> page = flowFormDao.findPageList(PageTool.getPage(query), query);
        return flowFormConverter.toResponse(page);
    }
}
