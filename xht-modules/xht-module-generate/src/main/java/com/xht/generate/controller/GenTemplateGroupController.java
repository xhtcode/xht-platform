package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.form.GenTemplateGroupFormRequest;
import com.xht.generate.domain.query.GenTemplateGroupQueryRequest;
import com.xht.generate.domain.response.GenTemplateGroupResponse;
import com.xht.generate.service.IGenTemplateGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "项目管理", description = "项目管理")
@RestController
@RequestMapping("/gen/template/group")
@RequiredArgsConstructor
public class GenTemplateGroupController {

    private final IGenTemplateGroupService genTemplateGroupService;

    /**
     * 创建项目
     *
     * @param formRequest 项目表单请求参数
     * @return 操作结果
     */
    @Operation(summary = "创建项目", description = "根据提供的请求参数创建一个新的项目")
    @PostMapping("/add")
    public R<Boolean> create(@Validated(value = {Groups.Create.class}) @RequestBody GenTemplateGroupFormRequest formRequest) {
        return R.ok(genTemplateGroupService.create(formRequest));
    }

    /**
     * 根据ID删除项目
     *
     * @param id 项目ID
     * @return 操作结果
     */
    @Operation(summary = "根据ID删除项目", description = "根据提供的项目ID删除项目")
    @PostMapping("/delete/{id}")
    public R<Boolean> removeById(@PathVariable @Parameter(description = "项目ID", required = true) Long id) {
        return R.ok(genTemplateGroupService.removeById(id));
    }

    /**
     * 根据ID更新项目
     *
     * @param formRequest 项目更新请求参数
     * @return 操作结果
     */
    @Operation(summary = "根据ID更新项目", description = "根据提供的项目更新请求参数更新项目")
    @PostMapping("/update")
     public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody GenTemplateGroupFormRequest formRequest) {
        return R.ok(genTemplateGroupService.updateById(formRequest));
    }



    /**
     * 根据ID查询项目
     *
     * @param id 项目ID
     * @return 项目信息
     */
    @Operation(summary = "根据ID查询项目", description = "根据提供的项目ID查询项目信息")
    @GetMapping("/get/{id}")
    public R<GenTemplateGroupResponse> findById(@PathVariable @Parameter(description = "项目ID", required = true) Long id) {
        return R.ok(genTemplateGroupService.getById(id));
    }

    /**
     * 根据提供的查询请求参数分页查询代码生成模板组信息
     *
     * @param queryRequest 查询参数
     * @return 代码生成模板组列表响应结果
     */
    @Operation(summary = "分页查询", description = "根据提供的查询请求参数分页查询代码生成模板组信息")
    @GetMapping("/page")
    public R<PageResponse<GenTemplateGroupResponse>> selectPage(GenTemplateGroupQueryRequest queryRequest) {
        return R.ok(genTemplateGroupService.selectPage(queryRequest));
    }

    /**
     * 查询所有代码生成模板组信息
     *
     * @return 代码生成模板组列表响应结果
     */
    @Operation(summary = "查询所有", description = "查询所有代码生成模板组信息")
    @GetMapping("/list")
    public R<List<GenTemplateGroupResponse>> findAll() {
        return R.ok(genTemplateGroupService.findAll());
    }

}

