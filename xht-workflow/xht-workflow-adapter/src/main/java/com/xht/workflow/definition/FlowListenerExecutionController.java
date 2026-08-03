package com.xht.workflow.definition;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.validation.Groups;
import com.xht.workflow.definition.domain.form.FlowListenerExecutionBasicForm;
import com.xht.workflow.definition.domain.query.FlowListenerExecutionQuery;
import com.xht.workflow.definition.domain.response.FlowListenerExecutionResponse;
import com.xht.workflow.definition.domain.vo.FlowListenerExecutionVO;
import com.xht.workflow.definition.service.IFlowListenerExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程扩展-执行监听器
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "流程扩展-执行监听器", description = "流程扩展-执行监听器相关的API")
@RestController
@RequestMapping("/workflow/listener/execution")
@RequiredArgsConstructor
public class FlowListenerExecutionController {

    private final IFlowListenerExecutionService flowListenerExecutionService;

    /**
     * 新增流程扩展-执行监听器
     *
     * @param form 流程扩展-执行监听器表单请求参数
     * @return 统一响应结果
     */
    @Operation(summary = "新增", description = "新增流程扩展-执行监听器")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody FlowListenerExecutionBasicForm form) {
        flowListenerExecutionService.create(form);
        return R.ok().build();
    }

    /**
     * 根据主键`id`删除流程扩展-执行监听器
     *
     * @param id 系统管理-字典表主键
     * @return 统一响应结果
     */
    @Operation(summary = "删除", description = "根据主键`id`删除流程扩展-执行监听器")
    @PostMapping("/remove/{id}")
    public R<Void> remove(@PathVariable Long id) {
        flowListenerExecutionService.remove(id);
        return R.ok().build();
    }

    /**
     * 根据主键`id`更新流程扩展-执行监听器
     *
     * @param form 流程扩展-执行监听器表单请求参数
     * @return 统一响应结果
     */
    @Operation(summary = "修改", description = "根据主键`id`更新流程扩展-执行监听器")
    @PostMapping("/update")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody FlowListenerExecutionBasicForm form) {
        flowListenerExecutionService.updateById(form);
        return R.ok().build();
    }

    /**
     * 根据主键`id`查询流程扩展-执行监听器
     *
     * @param listenerId 流程扩展-执行监听器主键
     * @return 流程扩展-执行监听器信息
     */
    @Operation(summary = "查询详情", description = "根据主键`listenerId`查询流程扩展-执行监听器")
    @GetMapping("/get/{listenerId}")
    public R<FlowListenerExecutionVO> findById(@PathVariable Long listenerId) {
        return R.ok().build(flowListenerExecutionService.findByListenerId(listenerId));
    }

    /**
     * 分页查询流程扩展-执行监听器
     *
     * @param query 流程扩展-执行监听器查询请求参数
     * @return 流程扩展-执行监听器分页信息
     */
    @Operation(summary = "分页查询", description = "分页查询流程扩展-执行监听器")
    @GetMapping("/page")
    public R<PageResponse<FlowListenerExecutionResponse>> findPageList(FlowListenerExecutionQuery query) {
        return R.ok().build(flowListenerExecutionService.findPageList(query));
    }

}
