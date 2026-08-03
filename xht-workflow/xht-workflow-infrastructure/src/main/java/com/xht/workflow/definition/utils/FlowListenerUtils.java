package com.xht.workflow.definition.utils;

import com.xht.framework.common.enums.XhtEnum;
import com.xht.framework.exception.UtilException;
import com.xht.workflow.common.exception.WorkFlowException;
import com.xht.workflow.definition.domain.form.FlowListenerBasicForm;

/**
 * 描述：
 *
 * @author xht
 **/
public final class FlowListenerUtils {

    /**
     * 工具类不允许实例化
     */
    private FlowListenerUtils() {
        throw new UtilException("工具类不允许实例化");
    }

    /**
     * 验证监听器表单
     *
     * @param xhtEnum 监听器表单
     */
    public static <T extends FlowListenerBasicForm<? extends XhtEnum<String>>> void validateListenerForm(T xhtEnum) {
        switch (xhtEnum.getListenerType()) {
            case JAVA_CLASS:
                xhtEnum.setExpressionValue(null);
                xhtEnum.setDelegateExpression(null);
                xhtEnum.setScriptFormat(null);
                xhtEnum.setScriptType(null);
                xhtEnum.setScriptContent(null);
                xhtEnum.setScriptResource(null);
                break;
            case EXPRESSION:
                xhtEnum.setJavaClass(null);
                xhtEnum.setDelegateExpression(null);
                xhtEnum.setScriptFormat(null);
                xhtEnum.setScriptType(null);
                xhtEnum.setScriptContent(null);
                xhtEnum.setScriptResource(null);
                break;
            case DELEGATE_EXPRESSION:
                xhtEnum.setJavaClass(null);
                xhtEnum.setExpressionValue(null);
                xhtEnum.setScriptFormat(null);
                xhtEnum.setScriptType(null);
                xhtEnum.setScriptContent(null);
                xhtEnum.setScriptResource(null);
                break;
            case SCRIPT:
                xhtEnum.setJavaClass(null);
                xhtEnum.setExpressionValue(null);
                xhtEnum.setDelegateExpression(null);
                break;
            default:
                throw new WorkFlowException("不支持的监听器类型：" + xhtEnum.getListenerType());
        }
    }

}
