package com.xht.workflow.definition;

import com.xht.framework.common.domain.R;
import com.xht.framework.validation.Groups;
import com.xht.workflow.definition.domain.form.FlowItemDefForm;
import com.xht.workflow.definition.domain.query.FlowItemDefPageQuery;
import com.xht.workflow.definition.domain.response.FlowItemDefResponse;
import com.xht.workflow.definition.service.IFlowItemDefService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述： 流程扩展-流程定义控制器
 *
 * @author xht
 **/
@Tag(name = "流程定义管理", description = "流程扩展-流程定义管理")
@Slf4j
@RestController
@RequestMapping("/workflow/definition")
@RequiredArgsConstructor
public class FlowItemDefController {

    private final IFlowItemDefService flowCategoryService;

    /**
     * 创建流程定义
     *
     * @param form 流程定义信息
     */
    @Operation(summary = "创建流程定义")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody FlowItemDefForm form) {
        flowCategoryService.create(form);
        return R.ok().build();
    }

    /**
     * 删除流程定义
     *
     * @param id 流程定义ID
     */
    @Operation(summary = "删除流程定义")
    @PostMapping("/remove/{id}")
    public R<Void> remove(@PathVariable Long id) {
        flowCategoryService.removeById(id);
        return R.ok().build();
    }

    /**
     * 修改流程定义
     *
     * @param form 流程定义信息
     */
    @Operation(summary = "修改流程定义")
    @PostMapping("/update/{id}")
    public R<Void> updateById(@PathVariable Long id, @Validated(value = {Groups.Update.class}) @RequestBody FlowItemDefForm form) {
        flowCategoryService.updateById(id, form);
        return R.ok().build();
    }

    /**
     * 获取流程定义详情
     *
     * @param id 流程定义ID
     * @return 流程定义详情
     */
    @Operation(summary = "获取流程定义详情")
    @GetMapping("/get/{id}")
    public R<FlowItemDefResponse> findById(@PathVariable Long id) {
        return R.ok().build(flowCategoryService.findById(id));
    }

    /**
     * 获取流程定义列表
     *
     * @param flowItemDefPageQuery 流程定义查询参数
     * @return 流程定义列表
     */
    @Operation(summary = "获取流程定义列表")
    @GetMapping("/list")
    public R<List<FlowItemDefResponse>> findList(FlowItemDefPageQuery flowItemDefPageQuery) {
        return R.ok().build(flowCategoryService.findList(flowItemDefPageQuery));
    }

}
