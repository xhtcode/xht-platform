package com.xht.workflow.definition.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.common.enums.XhtEnum;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.workflow.definition.converter.FlowListenerExecutionConverter;
import com.xht.workflow.definition.converter.FlowListenerFieldConverter;
import com.xht.workflow.definition.converter.FlowListenerTaskConverter;
import com.xht.workflow.definition.dao.FlowListenerExecutionDao;
import com.xht.workflow.definition.dao.FlowListenerFieldDao;
import com.xht.workflow.definition.dao.FlowListenerTaskDao;
import com.xht.workflow.definition.domain.query.FlowListenerExecutionQuery;
import com.xht.workflow.definition.domain.query.FlowListenerTaskQuery;
import com.xht.workflow.definition.domain.response.FlowListenerExecutionResponse;
import com.xht.workflow.definition.domain.response.FlowListenerTaskResponse;
import com.xht.workflow.definition.domain.vo.FlowListenerExecutionVO;
import com.xht.workflow.definition.domain.vo.FlowListenerTaskVO;
import com.xht.workflow.definition.entity.FlowListenerBasicEntity;
import com.xht.workflow.definition.entity.FlowListenerExecutionEntity;
import com.xht.workflow.definition.entity.FlowListenerTaskEntity;
import com.xht.workflow.definition.enums.ListenerFieldStatusEnum;
import com.xht.workflow.definition.enums.ListenerStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

/**
 * 描述： 流程定义服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowBpmnServiceImpl implements IFlowBpmnService {

    private final FlowListenerExecutionDao flowListenerExecutionDao;

    private final FlowListenerExecutionConverter flowListenerExecutionConverter;

    private final FlowListenerTaskDao flowListenerTaskDao;

    private final FlowListenerTaskConverter flowListenerTaskConverter;

    private final FlowListenerFieldDao flowListenerFieldDao;

    private final FlowListenerFieldConverter flowListenerFieldConverter;

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
        if (checkListener(executionEntity)) {
            result.setListener(flowListenerExecutionConverter.toResponse(executionEntity));
            result.setFields(flowListenerFieldConverter.toResponse(flowListenerFieldDao.findByListenerId(listenerId, ListenerFieldStatusEnum.SHOW)));
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
        query.setListenerStatus(ListenerStatusEnum.SHOW);
        Page<FlowListenerExecutionEntity> page = flowListenerExecutionDao.findPageList(PageTool.getPage(query), query);
        return flowListenerExecutionConverter.toResponse(page);
    }

    /**
     * 根据主键`listenerId`查询流程扩展-任务监听器
     *
     * @param listenerId 流程扩展-任务监听器主键
     * @return 流程扩展-任务监听器信息
     */
    @Override
    public FlowListenerTaskVO findById(Long listenerId) {
        FlowListenerTaskVO result = new FlowListenerTaskVO();
        FlowListenerTaskEntity taskEntity = flowListenerTaskDao.findById(listenerId);
        if (checkListener(taskEntity)) {
            result.setListener(flowListenerTaskConverter.toResponse(taskEntity));
            result.setFields(flowListenerFieldConverter.toResponse(flowListenerFieldDao.findByListenerId(listenerId, ListenerFieldStatusEnum.SHOW)));
        } else {
            result.setFields(Collections.emptyList());
        }
        return result;
    }

    public <T extends FlowListenerBasicEntity<? extends XhtEnum<String>>> boolean checkListener(T t) {
        return Objects.nonNull(t) && Objects.equals(t.getListenerStatus(), ListenerStatusEnum.SHOW);
    }

    /**
     * 分页查询流程扩展-任务监听器
     *
     * @param query 流程扩展-任务监听器查询请求参数
     * @return 流程扩展-任务监听器分页信息
     */
    @Override
    public PageResponse<FlowListenerTaskResponse> findPageList(FlowListenerTaskQuery query) {
        query.setListenerStatus(ListenerStatusEnum.SHOW);
        Page<FlowListenerTaskEntity> page = flowListenerTaskDao.findPageList(PageTool.getPage(query), query);
        return flowListenerTaskConverter.toResponse(page);
    }
}
