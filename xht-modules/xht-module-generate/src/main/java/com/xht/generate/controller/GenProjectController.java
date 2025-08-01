package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.request.GenProjectFormRequest;
import com.xht.generate.domain.request.GenProjectQueryRequest;
import com.xht.generate.domain.response.GenProjectResponse;
import com.xht.generate.service.IGenProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 项目管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "项目管理", description = "项目管理")
@RestController
@RequestMapping("/gen/project")
@RequiredArgsConstructor
public class GenProjectController {

    private final IGenProjectService genProjectService;

    /**
     * 创建项目
     *
     * @param formRequest 项目表单请求参数
     * @return 操作结果
     */
    @Operation(summary = "创建项目", description = "根据提供的请求参数创建一个新的项目")
    @PostMapping("/add")
    public R<Boolean> create(@Validated(value = {Groups.Create.class}) @RequestBody GenProjectFormRequest formRequest) {
        return R.ok(genProjectService.create(formRequest));
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
        return R.ok(genProjectService.removeById(id));
    }

    /**
     * 根据ID更新项目
     *
     * @param formRequest 项目更新请求参数
     * @return 操作结果
     */
    @Operation(summary = "根据ID更新项目", description = "根据提供的项目更新请求参数更新项目")
    @PostMapping("/update")
     public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody GenProjectFormRequest formRequest) {
        return R.ok(genProjectService.updateById(formRequest));
    }



    /**
     * 根据ID查询项目
     *
     * @param id 项目ID
     * @return 项目信息
     */
    @Operation(summary = "根据ID查询项目", description = "根据提供的项目ID查询项目信息")
    @GetMapping("/get/{id}")
    public R<GenProjectResponse> findById(@PathVariable @Parameter(description = "项目ID", required = true) Long id) {
        return R.ok(genProjectService.getById(id));
    }
    /**
     * 分页查询项目
     *
     * @param queryRequest 项目查询请求参数
     * @return 项目分页信息
     */
    @Operation(summary = "分页查询项目", description = "根据提供的查询请求参数分页查询项目信息")
    @GetMapping("/page")
    public R<PageResponse<GenProjectResponse>> selectPage(GenProjectQueryRequest queryRequest) {
        return R.ok(genProjectService.selectPage(queryRequest));
    }


}

