package com.xht.workflow;

import com.xht.framework.utils.spring.SpELUtils;
import org.flowable.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author xht
 **/
@SpringBootTest
public class ProcessManagerTest {


    @Autowired
    private RuntimeService runtimeService;


    @Test
    public void testStartProcessInstance() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("user", "admin");
        System.out.println("Starting process instance...");
        runtimeService.startProcessInstanceById("leaveProcess", "123", Collections.emptyMap());
        runtimeService.startProcessInstanceByKey("Process_02me0gh", "123", variables);
    }

    public static void main(String[] args) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "admin");
        variables.put("a", 1);
        System.out.println(SpELUtils.parseExpression("#{1+1}", variables, String.class));
        System.out.println(SpELUtils.parseExpression("#{a==1 and name=='admin'", variables, boolean.class,false));
        System.out.println(SpELUtils.parseExpression("#{a==1 or name=='lisi'}", variables, boolean.class));
    }
}
