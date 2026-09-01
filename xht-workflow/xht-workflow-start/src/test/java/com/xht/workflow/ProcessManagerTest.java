package com.xht.workflow;

import com.xht.framework.jackson.JsonUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.workflow.flowable.process.ProcessManager;
import com.xht.workflow.flowable.process.common.ProcessInstanceDTO;
import com.xht.workflow.flowable.process.common.ProcessStartBO;
import com.xht.workflow.flowable.process.common.ProcessStartBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 描述：
 *
 * @author xht
 **/
@SpringBootTest
public class ProcessManagerTest {


    @Autowired
    private ProcessManager processManager;


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


}
