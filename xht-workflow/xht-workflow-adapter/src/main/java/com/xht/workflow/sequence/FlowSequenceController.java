package com.xht.workflow.sequence;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.validation.Groups;
import com.xht.workflow.sequence.domain.form.FlowSequenceForm;
import com.xht.workflow.sequence.domain.query.FlowSequencePageQuery;
import com.xht.workflow.sequence.domain.response.FlowSequenceResponse;
import com.xht.workflow.sequence.service.IFlowSequenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述：流程序列管理
 *
 * @author xht
 **/
@Tag(name = "流程序列管理", description = "流程序列管理")
@Slf4j
@RestController
@RequestMapping("/workflow/sequence")
@RequiredArgsConstructor
public class FlowSequenceController {

    private final IFlowSequenceService flowSequenceService;

    /**
     * 创建流程分类
     *
     * @param form 流程分类信息
     */
    @PostMapping("/create")
    @Operation(summary = "创建流程分类")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody FlowSequenceForm form) {
        flowSequenceService.create(form);
        return R.ok().build();
    }

    /**
     * 删除流程分类
     *
     * @param ids 流程分类ID集合
     */
    @PostMapping("/remove")
    @Operation(summary = "删除流程分类")
    public R<Void> removeById(@RequestBody List<Long> ids) {
        flowSequenceService.removeById(ids);
        return R.ok().build();
    }

    /**
     * 修改流程分类
     *
     * @param form 流程分类信息
     */
    @PostMapping("/update")
    @Operation(summary = "修改流程分类")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody FlowSequenceForm form) {
        flowSequenceService.updateById(form);
        return R.ok().build();
    }

    /**
     * 获取流程分类详情
     *
     * @param id 流程分类ID
     * @return 流程分类详情
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "获取流程分类详情")
    public R<FlowSequenceResponse> findById(@PathVariable Long id) {
        return R.ok().build(flowSequenceService.findById(id));
    }

    /**
     * 分页查询流程分类
     *
     * @param query 流程类别查询参数
     * @return 分页结果
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询流程分类")
    public R<PageResponse<FlowSequenceResponse>> findPageList(FlowSequencePageQuery query) {
        return R.ok().build(flowSequenceService.findPageList(query));
    }

}
