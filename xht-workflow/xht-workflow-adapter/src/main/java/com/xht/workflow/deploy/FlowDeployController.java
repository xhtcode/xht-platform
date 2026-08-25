package com.xht.workflow.deploy;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.common.domain.query.WorkFlowPageQuery;
import com.xht.workflow.deploy.domain.query.DeployPageQuery;
import com.xht.workflow.deploy.domain.response.ProcessDefinitionResponse;
import com.xht.workflow.deploy.service.IFlowDeployService;
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
@RequestMapping("/workflow/process-definition")
@RequiredArgsConstructor
public class FlowDeployController {

    private final IFlowDeployService flowDeployService;

    /**
     * 分页查询流程定义
     *
     * @param query 流程部署查询参数
     * @return 分页流程定义列表
     */
    @Operation(summary = "分页查询流程定义")
    @GetMapping("/page")
    public R<PageResponse<ProcessDefinitionResponse>> findPage(DeployPageQuery query) {
        return R.ok().build(flowDeployService.findPage(query));
    }

    /**
     * 分页查询历史流程定义
     *
     * @param processDefKey 流程定义key
     * @param query         分页查询参数
     * @return 分页流程定义列表
     */
    @Operation(summary = "分页查询历史流程定义")
    @GetMapping("/history/{processDefKey}")
    public R<PageResponse<ProcessDefinitionResponse>> historyPage(@PathVariable("processDefKey") String processDefKey, WorkFlowPageQuery query) {
        return R.ok().build(flowDeployService.historyPage(processDefKey, query));
    }

    /**
     * 根据流程部署id 删除流程
     *
     * @param deployId 流程部署id
     */
    @Operation(summary = "根据流程部署id 删除流程")
    @PostMapping("/remove/{deployId}")
    public R<Void> deleteByDeployId(@PathVariable("deployId") String deployId) {
        flowDeployService.deleteByDeployId(deployId);
        return R.ok().build();
    }

}
