package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.form.GenTemplateFormRequest;
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
     * @param formRequest 模板表单请求参数
     * @return 操作结果
     */
    @Operation(summary = "创建模板", description = "根据提供的请求参数创建一个新的模板")
    @PostMapping("/add")
    public R<Boolean> create(@Validated(value = {Groups.Create.class}) @RequestBody GenTemplateFormRequest formRequest) {
        return R.ok(genTemplateService.create(formRequest));
    }

    /**
     * 根据ID删除模板
     *
     * @param id 模板ID
     * @return 操作结果
     */
    @Operation(summary = "根据ID删除模板", description = "根据提供的模板ID删除模板")
    @PostMapping("/delete/{id}")
    public R<Boolean> removeById(@PathVariable @Parameter(description = "模板ID", required = true) Long id) {
        return R.ok(genTemplateService.removeById(id));
    }

    /**
     * 根据ID更新模板
     *
     * @param formRequest 模板更新请求参数
     * @return 操作结果
     */
    @Operation(summary = "根据ID更新模板", description = "根据提供的模板更新请求参数更新模板")
    @PostMapping("/update")
     public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody GenTemplateFormRequest formRequest) {
        return R.ok(genTemplateService.updateById(formRequest));
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
        return R.ok(genTemplateService.getById(id));
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
