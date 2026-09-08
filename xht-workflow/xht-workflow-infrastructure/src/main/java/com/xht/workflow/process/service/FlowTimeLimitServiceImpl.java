package com.xht.workflow.process.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.code.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.workflow.process.converter.FlowTimeLimitConverter;
import com.xht.workflow.process.dao.FlowTimeLimitDao;
import com.xht.workflow.process.domain.query.FlowTimeLimitPageQuery;
import com.xht.workflow.process.domain.response.FlowTimeLimitResponse;
import com.xht.workflow.process.entity.FlowTimeLimitEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 描述： 流程时限服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowTimeLimitServiceImpl implements IFlowTimeLimitService {

    private final FlowTimeLimitDao flowTimeLimitDao;

    private final FlowTimeLimitConverter flowTimeLimitConverter;

    /**
     * 获取流程时限详情
     *
     * @param id 流程时限ID
     * @return 流程时限详情
     */
    @Override
    public FlowTimeLimitResponse findById(Long id) {
        return flowTimeLimitConverter.toResponse(flowTimeLimitDao.findOptionalById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST)));
    }

    /**
     * 分页查询流程时限
     *
     * @param query 流程时限查询参数
     * @return 流程时限分页信息
     */
    @Override
    public PageResponse<FlowTimeLimitResponse> findPageList(FlowTimeLimitPageQuery query) {
        Page<FlowTimeLimitEntity> page = flowTimeLimitDao.findPageList(PageTool.getPage(query), query);
        return flowTimeLimitConverter.toResponse(page);
    }
}
