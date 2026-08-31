package com.xht.workflow;

import cn.hutool.core.io.IoUtil;
import com.xht.workflow.flowable.utils.BpmnUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 描述：
 *
 * @author xht
 **/
@SpringBootTest
public class TestDelpoy {

    private final static FileInputStream FILE_INPUT_STREAM;

    static {
        try {
            FILE_INPUT_STREAM = new FileInputStream("E:\\MyProject\\xht-platform\\xht-workflow\\xht-workflow-start\\src\\test\\java\\com\\xht\\workflow\\流程模型.xml");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void testDeployment() {
        String xml = IoUtil.readUtf8(FILE_INPUT_STREAM);
        BpmnModel bpmnModel = BpmnUtils.getBpmnModel(xml);
        Process mainProcess = bpmnModel.getMainProcess();
        for (int i = 0; i < 10; i++) {
            Deployment holiday = repositoryService.createDeployment()
                    .name(mainProcess.getName())
                    .category("category")
                    .key(mainProcess.getId())
                    .addString("测试流程.bpmn20.xml", xml).deploy();
            System.out.println(i + "部署成功" + holiday.getId() + "name:\t" + holiday.getKey());
            // 查询刚部署出来的流程定义，验证是否生成
            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(holiday.getId())
                    .singleResult();
            System.out.println("流程定义ID："+pd.getId());
        }
    }

    @Test
    public void deleteDeployment() {
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment : list) {
            repositoryService.deleteDeployment(deployment.getId());
        }
    }

    @Test
    public void createProcessDefinitionQuery() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService
                .createProcessDefinitionQuery()
                .latestVersion();
        List<ProcessDefinition> list = processDefinitionQuery.list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println(processDefinition.getName());
        }
    }

}
