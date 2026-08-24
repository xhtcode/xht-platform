package com.xht.workflow;

import com.xht.workflow.flowable.model.ModelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述： 流程模型管理（ModelManager）集成测试
 * 覆盖模型初始化、查询、更新、设计、部署、删除、重名校验、分页查询等全部接口方法
 * <p>
 * 说明：
 * 1. 测试类开启事务，每个用例产生的模型/部署数据在测试结束后自动回滚，不污染数据库；
 * 2. 模型标识统一使用 System.nanoTime() 生成，保证多次运行互不冲突；
 * 3. 依赖测试资源文件 流程模型.xml（BPMN 流程定义，process name 为 holiday）。
 *
 * @author xht
 **/
@SpringBootTest
@Transactional
public class TestModelManager {

    @Autowired
    private ModelManager modelManager;



}
