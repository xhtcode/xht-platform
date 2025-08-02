package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.request.GenProjectTemplateFormRequest;
import com.xht.generate.domain.request.GenProjectTemplateQueryRequest;
import com.xht.generate.domain.response.GenProjectTemplateResponse;
import com.xht.generate.service.IGenProjectTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 项目模板管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "项目模板管理", description = "项目模板管理")
@RestController
@RequestMapping("/gen/project/template")
@RequiredArgsConstructor
public class GenProjectTemplateController {

    private final IGenProjectTemplateService genProjectTemplateService;

    /**
     * 创建项目模板
     *
     * @param formRequest 项目模板表单请求参数
     * @return 操作结果
     */
    @Operation(summary = "创建项目模板", description = "根据提供的请求参数创建一个新的项目模板")
    @PostMapping("/add")
    public R<Boolean> create(@Validated(value = {Groups.Create.class}) @RequestBody GenProjectTemplateFormRequest formRequest) {
        return R.ok(genProjectTemplateService.create(formRequest));
    }

    /**
     * 根据ID删除项目模板
     *
     * @param id 项目模板ID
     * @return 操作结果
     */
    @Operation(summary = "根据ID删除项目模板", description = "根据提供的项目模板ID删除项目模板")
    @PostMapping("/delete/{id}")
    public R<Boolean> removeById(@PathVariable @Parameter(description = "项目模板ID", required = true) Long id) {
        return R.ok(genProjectTemplateService.removeById(id));
    }

    /**
     * 根据ID更新项目模板
     *
     * @param formRequest 项目模板更新请求参数
     * @return 操作结果
     */
    @Operation(summary = "根据ID更新项目模板", description = "根据提供的项目模板更新请求参数更新项目模板")
    @PostMapping("/update")
     public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody GenProjectTemplateFormRequest formRequest) {
        return R.ok(genProjectTemplateService.updateById(formRequest));
    }



    /**
     * 根据ID查询项目模板
     *
     * @param id 项目模板ID
     * @return 项目模板信息
     */
    @Operation(summary = "根据ID查询项目模板", description = "根据提供的项目模板ID查询项目模板信息")
    @GetMapping("/get/{id}")
    public R<GenProjectTemplateResponse> findById(@PathVariable @Parameter(description = "项目模板ID", required = true) Long id) {
        return R.ok(genProjectTemplateService.getById(id));
    }
    /**
     * 分页查询项目模板
     *
     * @param queryRequest 项目模板查询请求参数
     * @return 项目模板分页信息
     */
    @Operation(summary = "分页查询项目模板", description = "根据提供的查询请求参数分页查询项目模板信息")
    @GetMapping("/page")
    public R<PageResponse<GenProjectTemplateResponse>> selectPage(GenProjectTemplateQueryRequest queryRequest) {
        return R.ok(genProjectTemplateService.selectPage(queryRequest));
    }


}

