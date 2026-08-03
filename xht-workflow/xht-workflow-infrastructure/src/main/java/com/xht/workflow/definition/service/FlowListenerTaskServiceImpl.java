package com.xht.workflow.definition.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.workflow.definition.converter.FlowListenerFieldConverter;
import com.xht.workflow.definition.converter.FlowListenerTaskConverter;
import com.xht.workflow.definition.dao.FlowListenerFieldDao;
import com.xht.workflow.definition.dao.FlowListenerTaskDao;
import com.xht.workflow.definition.domain.form.FlowListenerTaskBasicForm;
import com.xht.workflow.definition.domain.query.FlowListenerTaskQuery;
import com.xht.workflow.definition.domain.response.FlowListenerTaskResponse;
import com.xht.workflow.definition.domain.vo.FlowListenerTaskVO;
import com.xht.workflow.definition.entity.FlowListenerTaskEntity;
import com.xht.workflow.definition.utils.FlowListenerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;

/**
 * 流程扩展-任务监听器 Service实现类
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowListenerTaskServiceImpl implements IFlowListenerTaskService {

    private final FlowListenerTaskDao flowListenerTaskDao;

    private final FlowListenerTaskConverter flowListenerTaskConverter;

    private final FlowListenerFieldDao flowListenerFieldDao;

    private final FlowListenerFieldConverter flowListenerFieldConverter;

    /**
     * 创建流程扩展-任务监听器
     *
     * @param form 流程扩展-任务监听器表单请求参数
     */
    @Override
    public void create(FlowListenerTaskBasicForm form) {
        FlowListenerUtils.validateListenerForm(form);
        flowListenerTaskDao.saveTransactional(flowListenerTaskConverter.toEntity(form));
    }

    /**
     * 根据主键`id`删除流程扩展-任务监听器
     *
     * @param id 流程扩展-任务监听器主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        flowListenerTaskDao.removeById(id);
    }

    /**
     * 根据主键`id`更新流程扩展-任务监听器
     *
     * @param form 流程扩展-任务监听器表单请求参数
     */
    @Override
    public void updateById(FlowListenerTaskBasicForm form) {
        FlowListenerUtils.validateListenerForm(form);
        flowListenerTaskDao.updateFormRequest(form);
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
        if (Objects.nonNull(taskEntity)) {
            result.setListener(flowListenerTaskConverter.toResponse(taskEntity));
            result.setFields(flowListenerFieldConverter.toResponse(flowListenerFieldDao.findByListenerId(listenerId, null)));
        } else {
            result.setFields(Collections.emptyList());
        }
        return result;
    }

    /**
     * 分页查询流程扩展-任务监听器
     *
     * @param query 流程扩展-任务监听器查询请求参数
     * @return 流程扩展-任务监听器分页信息
     */
    @Override
    public PageResponse<FlowListenerTaskResponse> findPageList(FlowListenerTaskQuery query) {
        Page<FlowListenerTaskEntity> page = flowListenerTaskDao.findPageList(PageTool.getPage(query), query);
        return flowListenerTaskConverter.toResponse(page);
    }

}
