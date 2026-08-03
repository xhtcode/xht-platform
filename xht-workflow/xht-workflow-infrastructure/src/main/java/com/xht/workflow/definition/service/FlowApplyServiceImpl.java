package com.xht.workflow.definition.service;

import com.xht.workflow.definition.converter.FlowDefinitionConverter;
import com.xht.workflow.definition.dao.IFlowDefinitionDao;
import com.xht.workflow.definition.domain.response.FlowDefinitionResponse;
import com.xht.workflow.definition.enums.DefinitionStatusEnum;
import com.xht.workflow.common.constant.CategoryConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述： 申请服务实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowApplyServiceImpl implements IFlowApplyService {

    private final IFlowDefinitionDao flowCategoryDao;

    private final FlowDefinitionConverter flowDefinitionConverter;

    /**
     * 根据父级ID查询流程类别
     *
     * @param parentId 父级ID
     * @return 流程类别列表
     */
    @Override
    public List<FlowDefinitionResponse> getFlowCategoryItems(Long parentId) {
        if (parentId == null || parentId < 0) {
            parentId = CategoryConstant.DEFAULT_CATEGORY_ID;
        }
        return flowDefinitionConverter.toResponse(flowCategoryDao.findByParentId(parentId, DefinitionStatusEnum.NORMAL));
    }

}
