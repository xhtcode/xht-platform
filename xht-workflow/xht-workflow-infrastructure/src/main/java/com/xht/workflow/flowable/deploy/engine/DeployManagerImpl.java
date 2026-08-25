package com.xht.workflow.flowable.deploy.engine;

import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.flowable.common.bo.BpmnOrder;
import com.xht.workflow.flowable.common.enums.SuspendedStatus;
import com.xht.workflow.flowable.deploy.DeployManager;
import com.xht.workflow.flowable.deploy.common.DeployPageQueryBO;
import com.xht.workflow.flowable.utils.FlowableQueryUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.ProcessDefinitionQueryProperty;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 描述：
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class DeployManagerImpl implements DeployManager {

    private final RepositoryService repositoryService;

    /**
     * 分页查询流程定义
     *
     * @param query 查询条件
     */
    public void findPage(DeployPageQueryBO query) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().latestVersion();
        String processDefinitionKey = query.getProcessDefinitionKey();
        if (StringUtils.hasText(processDefinitionKey)) {
            processDefinitionQuery.processDefinitionKeyLike(FlowableQueryUtils.appendLikePrefix(processDefinitionKey));
        }
        String processDefinitionName = query.getProcessDefinitionName();
        if (StringUtils.hasText(processDefinitionName)) {
            processDefinitionQuery.processDefinitionNameLike(FlowableQueryUtils.appendLikePrefix(processDefinitionName));
        }
        String processDefinitionCategory = query.getProcessDefinitionCategory();
        if (StringUtils.hasText(processDefinitionCategory)) {
            processDefinitionQuery.processDefinitionCategory(processDefinitionCategory);
        }
        SuspendedStatus suspended = query.getSuspended();
        if (Objects.equals(suspended, SuspendedStatus.ACTIVE)) {
            processDefinitionQuery.active();
        }
        if (Objects.equals(suspended, SuspendedStatus.SUSPENDED)) {
            processDefinitionQuery.suspended();
        }
        List<BpmnOrder> orders = query.getOrders();
        if (!CollectionUtils.isEmpty(orders)) {
            for (BpmnOrder order : orders) {
                ThrowUtils.notNull(order, "排序参数[orders]不能为空");
                String name = order.getName();
                BpmnOrder.BpmnOrderType orderType = Objects.isNull(order.getOrderType()) ? BpmnOrder.BpmnOrderType.ASC : order.getOrderType();
                ThrowUtils.hasText(name, "排序字段不能为空");
                ProcessDefinitionQueryProperty sortProperty = switch (name) {
                    case "processDefinitionName" -> ProcessDefinitionQueryProperty.PROCESS_DEFINITION_NAME;
                    case "processDefinitionKey" -> ProcessDefinitionQueryProperty.PROCESS_DEFINITION_KEY;
                    case "processDefinitionCategory" -> ProcessDefinitionQueryProperty.PROCESS_DEFINITION_CATEGORY;
                    case "processDefinitionVersion" -> ProcessDefinitionQueryProperty.PROCESS_DEFINITION_VERSION;
                    default -> ProcessDefinitionQueryProperty.DEPLOYMENT_ID;
                };
                // 映射前端字段 -> Flowable内置属性
                processDefinitionQuery.orderBy(sortProperty);
                if (orderType.equals(BpmnOrder.BpmnOrderType.DESC)) {
                    processDefinitionQuery.desc();
                } else {
                    processDefinitionQuery.asc();
                }
            }
        }


    }


}
