package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.form.GenTemplateBasicForm;
import com.xht.generate.domain.response.GenTemplateResponse;
import com.xht.generate.service.IGenTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模板管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "模板管理", description = "模板管理")
@RestController
@RequestMapping("/gen/template")
@RequiredArgsConstructor
public class GenTemplateController {

    private final IGenTemplateService genTemplateService;

    /**
     * 创建模板
     *
     * @param form 模板表单请求参数
     * @return 统一响应结果
     */
    @Operation(summary = "创建模板", description = "根据提供的请求参数创建一个新的模板")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody GenTemplateBasicForm form) {
        genTemplateService.create(form);
        return R.ok();
    }

    /**
     * 根据ID删除模板
     *
     * @param id 模板ID
     * @return 统一响应结果
     */
    @Operation(summary = "根据ID删除模板", description = "根据提供的模板ID删除模板")
    @PostMapping("/remove/{id}")
    public R<Void> removeById(@PathVariable @Parameter(description = "模板ID", required = true) Long id) {
        genTemplateService.removeById(id);
        return R.ok();
    }

    /**
     * 根据ID更新模板
     *
     * @param form 模板更新请求参数
     * @return 统一响应结果
     */
    @Operation(summary = "根据ID更新模板", description = "根据提供的模板更新请求参数更新模板")
    @PostMapping("/update")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody GenTemplateBasicForm form) {
        genTemplateService.updateById(form);
        return R.ok();
    }

    /**
     * 根据ID查询模板
     *
     * @param id 模板ID
     * @return 模板信息
     */
    @Operation(summary = "根据ID查询模板", description = "根据提供的模板ID查询模板信息")
    @GetMapping("/get/{id}")
    public R<GenTemplateResponse> findById(@PathVariable @Parameter(description = "模板ID", required = true) Long id) {
        return R.ok(genTemplateService.findById(id));
    }

    /**
     * 根据模板组ID获取模板列表
     *
     * @param groupId 模板组ID
     * @return 模板响应列表
     */
    @GetMapping("/list/{groupId}")
    public R<List<GenTemplateResponse>> listByGroupId(@PathVariable("groupId") String groupId) {
        return R.ok(genTemplateService.listByGroupId(groupId));
    }

}
