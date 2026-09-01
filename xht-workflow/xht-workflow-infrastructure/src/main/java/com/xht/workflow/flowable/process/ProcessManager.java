package com.xht.workflow.flowable.process;

import com.xht.workflow.flowable.definition.ProcessDefinitionManager;
import com.xht.workflow.flowable.definition.common.ProcessDefinitionDTO;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.RuntimeService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述： 流程管理器
 *
 * @author xht
 **/
@Component
@RequiredArgsConstructor
public class ProcessManager {

    private final RuntimeService runtimeService;

    /**
     * 启动流程实例
     *
     * @param processDefinitionId 流程定义ID
     * @param businessKey         业务Key
     * @param variables           变量
     */
    public void startProcessInstance(String processDefinitionId, String businessKey, Map<String, Object> variables) {
        runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables);
    }

}
