package com.xht.workflow.definition.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.workflow.definition.converter.FlowListenerExecutionConverter;
import com.xht.workflow.definition.converter.FlowListenerFieldConverter;
import com.xht.workflow.definition.dao.FlowListenerExecutionDao;
import com.xht.workflow.definition.dao.FlowListenerFieldDao;
import com.xht.workflow.definition.domain.form.FlowListenerExecutionBasicForm;
import com.xht.workflow.definition.domain.query.FlowListenerExecutionQuery;
import com.xht.workflow.definition.domain.response.FlowListenerExecutionResponse;
import com.xht.workflow.definition.domain.vo.FlowListenerExecutionVO;
import com.xht.workflow.definition.entity.FlowListenerExecutionEntity;
import com.xht.workflow.definition.utils.FlowListenerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;

/**
 * 流程扩展-执行监听器 Service实现类
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowListenerExecutionServiceImpl implements IFlowListenerExecutionService {

    private final FlowListenerExecutionDao flowListenerExecutionDao;

    private final FlowListenerExecutionConverter flowListenerExecutionConverter;

    private final FlowListenerFieldDao flowListenerFieldDao;

    private final FlowListenerFieldConverter flowListenerFieldConverter;

    /**
     * 创建流程扩展-执行监听器
     *
     * @param form 流程扩展-执行监听器表单请求参数
     */
    @Override
    public void create(FlowListenerExecutionBasicForm form) {
        FlowListenerUtils.validateListenerForm(form);
        flowListenerExecutionDao.saveTransactional(flowListenerExecutionConverter.toEntity(form));
    }

    /**
     * 根据主键`id`删除流程扩展-执行监听器
     *
     * @param id 流程扩展-执行监听器主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        flowListenerExecutionDao.removeById(id);
    }

    /**
     * 根据主键`id`更新流程扩展-执行监听器
     *
     * @param form 流程扩展-执行监听器表单请求参数
     */
    @Override
    public void updateById(FlowListenerExecutionBasicForm form) {
        FlowListenerUtils.validateListenerForm(form);
        flowListenerExecutionDao.updateFormRequest(form);
    }

    /**
     * 根据主键`listenerId`查询流程扩展-执行监听器
     *
     * @param listenerId 流程扩展-执行监听器主键
     * @return 流程扩展-执行监听器信息
     */
    @Override
    public FlowListenerExecutionVO findByListenerId(Long listenerId) {
        FlowListenerExecutionVO result = new FlowListenerExecutionVO();
        FlowListenerExecutionEntity executionEntity = flowListenerExecutionDao.findById(listenerId);
        if (Objects.nonNull(executionEntity)) {
            result.setListener(flowListenerExecutionConverter.toResponse(executionEntity));
            result.setFields(flowListenerFieldConverter.toResponse(flowListenerFieldDao.findByListenerId(listenerId, null)));
        } else {
            result.setFields(Collections.emptyList());
        }
        return result;
    }

    /**
     * 分页查询流程扩展-执行监听器
     *
     * @param query 流程扩展-执行监听器查询请求参数
     * @return 流程扩展-执行监听器分页信息
     */
    @Override
    public PageResponse<FlowListenerExecutionResponse> findPageList(FlowListenerExecutionQuery query) {
        Page<FlowListenerExecutionEntity> page = flowListenerExecutionDao.findPageList(PageTool.getPage(query), query);
        return flowListenerExecutionConverter.toResponse(page);
    }

}
