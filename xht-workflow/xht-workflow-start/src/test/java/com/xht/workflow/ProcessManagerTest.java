package com.xht.workflow;

import com.xht.framework.jackson.JsonUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.workflow.flowable.process.ProcessManager;
import com.xht.workflow.flowable.process.common.*;
import org.flowable.engine.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @author xht
 **/
@SpringBootTest
public class ProcessManagerTest {

    @Autowired
    private ProcessManager processManager;
    @Autowired
    private TaskService taskService;
    private static final String processInstanceId = "f28ac643-a5e9-11f1-8ed9-d6085339c355";

    @Test
    public void testStartProcessInstance() {
        ProcessStartBO processStartBO = ProcessStartBuilder.builder()
                .processDefinitionKey("Process_02me0gh")
                .businessKey(IdUtils.simpleUUID())
                .comment("启动意见")
                .variable("user","admin")
                .variable("initiator","admin2")
                .build();
        ProcessInstanceDTO processInstanceDTO = processManager.startProcessInstance(processStartBO);
        System.out.println(JsonUtils.toJsonString(processInstanceDTO));
    }

    @Test
    public void testSaveComment() {
        List<HistoricTaskDTO> historicTaskByProcessInstanceId = processManager.findHistoricTaskByProcessInstanceId(processInstanceId);
        Map<String, List<CommentDTO>> commentProcessInstanceId =
                processManager.findCommentProcessInstanceId(processInstanceId);
        for (HistoricTaskDTO historicTaskDTO : historicTaskByProcessInstanceId) {
            System.out.println(JsonUtils.toJsonString(historicTaskDTO));
            System.out.println(JsonUtils.toJsonString(commentProcessInstanceId.getOrDefault(historicTaskDTO.getTaskId(), Collections.emptyList())));
        }
    }

    @Test
    public void generateDiagram() throws Exception {
        TaskCompleteBO taskCompleteBO = TaskCompleteBuilder.builder()
                .taskId("f28d103a-a5e9-11f1-8ed9-d6085339c355")
                .userId("admin")
                .comment("同意")
                .variable("approve", true)
                .build();
        processManager.taskComplete(taskCompleteBO);
    }

}
