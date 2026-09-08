package com.xht.workflow.process.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.code.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.workflow.process.converter.FlowBusinessConverter;
import com.xht.workflow.process.dao.FlowBusinessDao;
import com.xht.workflow.process.domain.query.FlowBusinessPageQuery;
import com.xht.workflow.process.domain.response.FlowBusinessResponse;
import com.xht.workflow.process.entity.FlowBusinessEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 描述： 流程业务服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowBusinessServiceImpl implements IFlowBusinessService {

    private final FlowBusinessDao flowBusinessDao;

    private final FlowBusinessConverter flowBusinessConverter;

    /**
     * 获取流程业务详情
     *
     * @param id 流程业务ID
     * @return 流程业务详情
     */
    @Override
    public FlowBusinessResponse findById(Long id) {
        return flowBusinessConverter.toResponse(flowBusinessDao.findOptionalById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST)));
    }

    /**
     * 分页查询流程业务
     *
     * @param query 流程业务查询参数
     * @return 流程业务分页信息
     */
    @Override
    public PageResponse<FlowBusinessResponse> findPageList(FlowBusinessPageQuery query) {
        Page<FlowBusinessEntity> page = flowBusinessDao.findPageList(PageTool.getPage(query), query);
        return flowBusinessConverter.toResponse(page);
    }
}
