package com.xht.workflow.process;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.common.domain.query.WorkFlowPageQuery;
import com.xht.workflow.process.domain.query.ProcessDefinitionPageQuery;
import com.xht.workflow.process.domain.response.ProcessDefinitionResponse;
import com.xht.workflow.process.service.IFlowProcessDefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 描述： 流程部署控制器
 *
 * @author xht
 **/
@Tag(name = "流程部署控制器", description = "流程部署管理")
@Slf4j
@RestController
@RequestMapping("/workflow/process/definition")
@RequiredArgsConstructor
public class FlowProcessDefinitionController {

    private final IFlowProcessDefinitionService flowDeployService;

    /**
     * 分页查询流程定义
     *
     * @param processDefinitionPageQuery 流程部署查询参数
     * @return 分页流程定义列表
     */
    @Operation(summary = "分页查询流程定义")
    @GetMapping("/page")
    public R<PageResponse<ProcessDefinitionResponse>> findPage(ProcessDefinitionPageQuery processDefinitionPageQuery) {
        return R.ok().build(flowDeployService.findPage(processDefinitionPageQuery));
    }

    /**
     * 分页查询历史流程定义
     *
     * @param processDefKey 流程定义key
     * @param workFlowPageQuery         分页查询参数
     * @return 分页流程定义列表
     */
    @Operation(summary = "分页查询历史流程定义")
    @GetMapping("/history/{processDefKey}")
    public R<PageResponse<ProcessDefinitionResponse>> historyPage(@PathVariable String processDefKey, WorkFlowPageQuery workFlowPageQuery) {
        return R.ok().build(flowDeployService.historyPage(processDefKey, workFlowPageQuery));
    }

    /**
     * 根据流程部署id 删除流程
     *
     * @param deployId 流程部署id
     */
    @Operation(summary = "根据流程部署id 删除流程")
    @PostMapping("/remove/{deployId}")
    public R<Void> deleteByDeployId(@PathVariable String deployId) {
        flowDeployService.deleteByDeployId(deployId);
        return R.ok().build();
    }

}
