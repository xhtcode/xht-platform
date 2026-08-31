package com.xht.workflow.definition;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.validation.Groups;
import com.xht.workflow.definition.domain.form.FlowFormForm;
import com.xht.workflow.definition.domain.query.FlowFormPageQuery;
import com.xht.workflow.definition.domain.response.FlowFormResponse;
import com.xht.workflow.definition.service.IFlowFormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述： 流程扩展-流程表单控制器
 *
 * @author xht
 **/
@Tag(name = "流程表单管理", description = "流程扩展-流程表单管理")
@Slf4j
@RestController
@RequestMapping("/workflow/form")
@RequiredArgsConstructor
public class FlowFormController {

    private final IFlowFormService flowFormService;

    /**
     * 创建流程表单
     *
     * @param form 流程表单信息
     */
    @Operation(summary = "创建流程表单")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody FlowFormForm form) {
        flowFormService.create(form);
        return R.ok().build();
    }

    /**
     * 删除流程表单
     *
     * @param id 流程表单ID
     */
    @Operation(summary = "删除流程表单")
    @PostMapping("/remove/{id}")
    public R<Void> remove(@PathVariable Long id) {
        flowFormService.removeById(id);
        return R.ok().build();
    }

    /**
     * 修改流程表单
     *
     * @param id   流程表单ID
     * @param form 流程表单信息
     */
    @Operation(summary = "修改流程表单")
    @PostMapping("/update/{id}")
    public R<Void> updateById(@PathVariable Long id, @Validated(value = {Groups.Update.class}) @RequestBody FlowFormForm form) {
        flowFormService.updateById(id, form);
        return R.ok().build();
    }

    /**
     * 获取流程表单详情
     *
     * @param id 流程表单ID
     * @return 流程表单详情
     */
    @Operation(summary = "获取流程表单详情")
    @GetMapping("/get/{id}")
    public R<FlowFormResponse> findById(@PathVariable Long id) {
        return R.ok().build(flowFormService.findById(id));
    }

    /**
     * 分页查询流程表单
     *
     * @param query 流程表单查询参数
     * @return 流程表单分页信息
     */
    @Operation(summary = "分页查询流程表单")
    @GetMapping("/page")
    public R<PageResponse<FlowFormResponse>> findPageList(FlowFormPageQuery query) {
        return R.ok().build(flowFormService.findPageList(query));
    }

}
