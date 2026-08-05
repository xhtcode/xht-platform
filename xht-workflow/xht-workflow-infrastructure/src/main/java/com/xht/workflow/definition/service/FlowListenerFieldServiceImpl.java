package com.xht.workflow.definition.service;

import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.definition.converter.FlowListenerFieldConverter;
import com.xht.workflow.definition.dao.FlowListenerExecutionDao;
import com.xht.workflow.definition.dao.FlowListenerFieldDao;
import com.xht.workflow.definition.dao.FlowListenerTaskDao;
import com.xht.workflow.definition.domain.form.FlowListenerFieldForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 描述： 流程扩展-监听器（字段管理）ServiceImpl
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowListenerFieldServiceImpl implements IFlowListenerFieldService {

    private final FlowListenerFieldDao flowListenerFieldDao;

    private final FlowListenerFieldConverter flowListenerFieldConverter;

    private final FlowListenerExecutionDao flowListenerExecutionDao;

    private final FlowListenerTaskDao flowListenerTaskDao;

    /**
     * 创建流程扩展-监听器
     *
     * @param form         流程扩展-监听器表单请求参数
     * @param listenerType 监听器类型
     */
    @Override
    public void create(FlowListenerFieldForm form, ListenerType listenerType) {
        validateFormRequest(form, listenerType);
        flowListenerFieldDao.saveTransactional(flowListenerFieldConverter.toEntity(form));
    }

    /**
     * 根据主键`id`删除流程扩展-监听器
     *
     * @param id 流程扩展-监听器主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        flowListenerFieldDao.removeById(id);
    }

    /**
     * 根据主键`id`更新流程扩展-监听器
     *
     * @param form         流程扩展-监听器表单请求参数
     * @param listenerType 监听器类型
     */
    @Override
    public void updateById(FlowListenerFieldForm form, ListenerType listenerType) {
        validateFormRequest(form, listenerType);
        flowListenerFieldDao.updateFormRequest(form);
    }

    /**
     * 校验流程扩展-监听器
     *
     * @param form         流程扩展-监听器表单请求参数
     * @param listenerType 监听器类型
     */
    public void validateFormRequest(FlowListenerFieldForm form, ListenerType listenerType) {
        ThrowUtils.notNull(form, "流程扩展-监听器表单请求参数不能为空");
        ThrowUtils.notNull(listenerType, "监听器类型不能为空");
        if (Objects.equals(listenerType, ListenerType.Execution)) {
            boolean existsListener = flowListenerExecutionDao.existsById(form.getListenerId());
            ThrowUtils.throwIf(!existsListener, "查询不到监听器");
        } else {
            boolean existsListener = flowListenerTaskDao.existsById(form.getListenerId());
            ThrowUtils.throwIf(!existsListener, "查询不到监听器");
        }
    }
}
