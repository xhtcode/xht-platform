package com.xht.workflow.sequence.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.code.BusinessErrorCode;
import com.xht.framework.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.workflow.sequence.converter.FlowSequenceConverter;
import com.xht.workflow.sequence.dao.IFlowSequenceDao;
import com.xht.workflow.sequence.domain.form.FlowSequenceForm;
import com.xht.workflow.sequence.domain.query.FlowSequencePageQuery;
import com.xht.workflow.sequence.domain.response.FlowSequenceResponse;
import com.xht.workflow.sequence.entity.FlowSequenceEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：流程序列管理表单
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowSequenceServiceImpl implements IFlowSequenceService {

    private final IFlowSequenceDao flowSequenceDao;

    private final FlowSequenceConverter flowSequenceConverter;

    /**
     * 创建流程序列
     *
     * @param form 流程序列信息
     */
    @Override
    public void create(FlowSequenceForm form) {
        Boolean checkSequenceCode = flowSequenceDao.checkSequenceCode(null, form.getSequenceCode());
        ThrowUtils.throwIf(checkSequenceCode, BusinessErrorCode.DATA_EXIST, "流程序列编码已存在");
        FlowSequenceEntity entity = flowSequenceConverter.toEntity(form);
        flowSequenceDao.saveTransactional(entity);
    }

    /**
     * 删除流程序列
     *
     * @param ids 流程序列ID集合
     */
    @Override
    public void removeById(List<Long> ids) {
        flowSequenceDao.removeAllById(ids);
    }

    /**
     * 修改流程序列
     *
     * @param form 流程序列信息
     */
    @Override
    public void updateById(FlowSequenceForm form) {
        Long id = form.getId();
        ThrowUtils.notNull(id);
        flowSequenceDao.findOptionalById(id).orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST));
        // 检查流程序列是否存在
        Boolean checkSequenceCode = flowSequenceDao.checkSequenceCode(null, form.getSequenceCode());
        ThrowUtils.throwIf(checkSequenceCode, BusinessErrorCode.DATA_EXIST, "流程序列编码已存在");
        flowSequenceDao.updateRequest(form);
    }

    /**
     * 获取流程序列详情
     *
     * @param id 流程序列ID
     * @return 流程序列详情
     */
    @Override
    public FlowSequenceResponse findById(Long id) {
        return flowSequenceConverter.toResponse(flowSequenceDao.findById(id));
    }

    /**
     * 分页查询流程序列
     *
     * @param query 流程类别查询参数
     * @return 分页结果
     */
    @Override
    public PageResponse<FlowSequenceResponse> findPageList(FlowSequencePageQuery query) {
        Page<FlowSequenceEntity> page = flowSequenceDao.findPageList(PageTool.getPage(query), query);
        return flowSequenceConverter.toResponse(page);
    }

}
