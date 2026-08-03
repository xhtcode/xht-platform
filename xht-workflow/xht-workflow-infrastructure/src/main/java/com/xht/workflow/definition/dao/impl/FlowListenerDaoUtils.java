package com.xht.workflow.definition.dao.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.framework.common.enums.XhtEnum;
import com.xht.workflow.definition.domain.form.FlowListenerBasicForm;
import com.xht.workflow.definition.entity.FlowListenerBasicEntity;

/**
 * 描述： 流程扩展-任务监听器 Dao实现类
 *
 * @author xht
 **/
public class FlowListenerDaoUtils {


    /**
     * 描述：填充更新条件
     *
     * @param updateWrapper 更新条件
     * @param form                表单
     */
    public static <Entity extends FlowListenerBasicEntity<? extends XhtEnum<String>>
            , Form extends FlowListenerBasicForm<? extends XhtEnum<String>>> void fillLambdaUpdateWrapper(LambdaUpdateWrapper<Entity> updateWrapper, Form form) {
        updateWrapper.set(FlowListenerBasicEntity::getEventType, form.getEventType());
        updateWrapper.set(FlowListenerBasicEntity::getListenerType, form.getListenerType());
        updateWrapper.set(FlowListenerBasicEntity::getJavaClass, form.getJavaClass());
        updateWrapper.set(FlowListenerBasicEntity::getExpressionValue, form.getExpressionValue());
        updateWrapper.set(FlowListenerBasicEntity::getDelegateExpression, form.getDelegateExpression());
        updateWrapper.set(FlowListenerBasicEntity::getScriptFormat, form.getScriptFormat());
        updateWrapper.set(FlowListenerBasicEntity::getScriptType, form.getScriptType());
        updateWrapper.set(FlowListenerBasicEntity::getScriptContent, form.getScriptContent());
        updateWrapper.set(FlowListenerBasicEntity::getScriptResource, form.getScriptResource());
        updateWrapper.set(FlowListenerBasicEntity::getListenerStatus, form.getListenerStatus());
        updateWrapper.eq(FlowListenerBasicEntity::getId, form.getId());
    }

}
