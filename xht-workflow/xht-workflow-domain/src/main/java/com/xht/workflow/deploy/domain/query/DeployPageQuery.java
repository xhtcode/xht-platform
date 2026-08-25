package com.xht.workflow.deploy.domain.query;

import com.xht.workflow.common.domain.enums.SuspendedStatus;
import com.xht.workflow.common.domain.query.WorkFlowPageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述： 流程部署分页查询
 *
 * @author xht
 **/
@Data
@Schema(description = "流程部署分页查询")
public class DeployPageQuery extends WorkFlowPageQuery {

    /**
     * 流程定义key
     */
    private String processDefinitionKey;

    /**
     * 流程定义名称
     */
    private String processDefinitionName;

    /**
     * 流程分类
     */
    private String processDefinitionCategory;

    /**
     * 流程状态
     */
    private SuspendedStatus suspended;

}
