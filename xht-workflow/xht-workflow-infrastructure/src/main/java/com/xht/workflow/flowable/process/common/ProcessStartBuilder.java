package com.xht.workflow.flowable.process.common;

import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.common.domain.enums.ProcStartTypeEnum;
import com.xht.workflow.flowable.common.BpmnBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述： 流程启动参数 构建器
 *
 * @author xht
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessStartBuilder implements BpmnBuilder<ProcessStartBO> {

    /**
     * 流程启动类型
     */
    private ProcStartTypeEnum procStartType;

    /**
     * 流程启动值
     */
    private String procStartValue;

    /**
     * 业务key
     */
    private String businessKey;

    /**
     * 流程启动意见
     */
    private String comment;

    /**
     * 流程变量
     */
    private final Map<String, Object> variables = new HashMap<>();

    /**
     * 构建流程启动参数建造者
     *
     * @return 流程启动参数建造者
     */
    public static ProcessStartBuilder builder() {
        return new ProcessStartBuilder();
    }

    /**
     * 设置流程启动值
     *
     * @param processDefinitionId 流程定义id
     * @return 流程启动参数建造者
     */
    public ProcessStartBuilder processDefinitionId(String processDefinitionId) {
        ThrowUtils.hasText(processDefinitionId, "流程定义id不能为空");
        this.procStartValue = processDefinitionId;
        this.procStartType = ProcStartTypeEnum.ID;
        return this;
    }

    /**
     * 设置流程启动值
     *
     * @param processDefinitionKey 流程定义key
     * @return 流程启动参数建造者
     */
    public ProcessStartBuilder processDefinitionKey(String processDefinitionKey) {
        ThrowUtils.hasText(processDefinitionKey, "流程定义key不能为空");
        this.procStartValue = processDefinitionKey;
        this.procStartType = ProcStartTypeEnum.KEY;
        return this;
    }

    /**
     * 设置业务key
     *
     * @param businessKey 业务key
     * @return 流程启动参数建造者
     */
    public ProcessStartBuilder businessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    /**
     * 设置流程启动意见
     *
     * @param comment 流程启动意见
     * @return 流程启动参数建造者
     */
    public ProcessStartBuilder comment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * 添加单个流程变量
     *
     * @param key   流程变量key
     * @param value 流程变量值
     * @return 流程启动参数建造者
     */
    public ProcessStartBuilder variable(String key, Object value) {
        ThrowUtils.hasText(key, "流程变量key不能为空");
        this.variables.put(key, value);
        return this;
    }

    /**
     * 批量添加流程变量
     *
     * @param variables 流程变量
     * @return 流程启动参数建造者
     */
    public ProcessStartBuilder variables(Map<String, Object> variables) {
        if (CollectionUtils.isEmpty(variables)) {
            return this;
        }
        this.variables.putAll(variables);
        return this;
    }

    /**
     * 删除流程变量
     *
     * @param key 流程变量key
     * @return 流程启动参数建造者
     */
    public ProcessStartBuilder removeVariable(String key) {
        this.variables.remove(key);
        return this;
    }

    /**
     * 构建流程启动参数
     */
    @Override
    public ProcessStartBO build() {
        ThrowUtils.notNull(procStartType, "流程启动类型不能为空");
        ThrowUtils.hasText(procStartValue, "流程启动值不能为空");
        ThrowUtils.hasText(businessKey, "业务key不能为空");
        ProcessStartBO processStartBO = new ProcessStartBO();
        processStartBO.setProcStartType(procStartType);
        processStartBO.setProcStartValue(procStartValue);
        processStartBO.setBusinessKey(businessKey);
        processStartBO.setComment(comment);
        processStartBO.setVariables(variables);
        return processStartBO;
    }
}
