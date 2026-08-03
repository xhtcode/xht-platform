package com.xht.workflow.definition;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.definition.domain.query.FlowListenerExecutionQuery;
import com.xht.workflow.definition.domain.query.FlowListenerTaskQuery;
import com.xht.workflow.definition.domain.response.FlowListenerExecutionResponse;
import com.xht.workflow.definition.domain.response.FlowListenerTaskResponse;
import com.xht.workflow.definition.domain.vo.FlowListenerExecutionVO;
import com.xht.workflow.definition.domain.vo.FlowListenerTaskVO;
import com.xht.workflow.definition.service.IFlowBpmnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：BPMN 支持 控制器
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "流程扩展-BPMN 支持 控制器", description = "流程扩展-BPMN 支持 控制器相关的API")
@RestController
@RequestMapping("/workflow/bpmn")
@RequiredArgsConstructor
public class BpmnController {

    private final IFlowBpmnService iFlowBpmnService;

    /**
     * 根据主键`listenerId`查询流程扩展-执行监听器
     *
     * @param listenerId 流程扩展-执行监听器主键
     * @return 流程扩展-执行监听器信息
     */
    @GetMapping("/listener/execution/get/{listenerId}")
    public R<FlowListenerExecutionVO> findListenerExecutionById(@PathVariable Long listenerId) {
        return R.ok().build(iFlowBpmnService.findListenerExecutionById(listenerId));
    }

    /**
     * 分页查询BPMN可用的流程扩展-执行监听器（仅显示状态）
     *
     * @param query 流程扩展-执行监听器查询请求参数
     * @return 流程扩展-执行监听器分页信息
     */
    @Operation(summary = "BPMN分页查询", description = "分页查询BPMN可用的流程扩展-执行监听器（仅显示状态）")
    @GetMapping("/listener/execution/page")
    public R<PageResponse<FlowListenerExecutionResponse>> findListenerExecutionPage(FlowListenerExecutionQuery query) {
        return R.ok().build(iFlowBpmnService.findListenerExecutionPage(query));
    }

    /**
     * 根据主键`listenerId`查询流程扩展-任务监听器
     *
     * @param listenerId 流程扩展-任务监听器主键
     * @return 流程扩展-任务监听器信息
     */
    @Operation(summary = "BPMN查询", description = "根据主键`listenerId`查询流程扩展-任务监听器")
    @GetMapping("/listener/task/get/{listenerId}")
    public R<FlowListenerTaskVO> findListenerTaskById(@PathVariable Long listenerId) {
        return R.ok().build(iFlowBpmnService.findListenerTaskById(listenerId));
    }

    /**
     * 分页查询BPMN可用的流程扩展-执行监听器（仅显示状态）
     *
     * @param query 流程扩展-执行监听器查询请求参数
     * @return 流程扩展-执行监听器分页信息
     */
    @Operation(summary = "BPMN分页查询", description = "分页查询BPMN可用的流程扩展-执行监听器（仅显示状态）")
    @GetMapping("/listener/task/page")
    public R<PageResponse<FlowListenerTaskResponse>> findListenerTaskPage(FlowListenerTaskQuery query) {
        return R.ok().build(iFlowBpmnService.findListenerTaskPage(query));
    }
}
