package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.request.GenProjectDataSourceFormRequest;
import com.xht.generate.domain.request.GenProjectDataSourceQueryRequest;
import com.xht.generate.domain.response.GenProjectDataSourceResponse;
import com.xht.generate.service.IGenProjectDataSourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 项目数据源管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "项目数据源管理", description = "项目数据源管理")
@RestController
@RequestMapping("/gen/project/data/source")
@RequiredArgsConstructor
public class GenProjectDataSourceController {

    private final IGenProjectDataSourceService genProjectDataSourceService;

    /**
     * 创建项目数据源
     *
     * @param formRequest 项目数据源表单请求参数
     * @return 操作结果
     */
    @Operation(summary = "创建项目数据源", description = "根据提供的请求参数创建一个新的项目数据源")
    @PostMapping("/add")
    public R<Boolean> create(@Validated(value = {Groups.Create.class}) @RequestBody GenProjectDataSourceFormRequest formRequest) {
        return R.ok(genProjectDataSourceService.create(formRequest));
    }

    /**
     * 根据ID删除项目数据源
     *
     * @param id 项目数据源ID
     * @return 操作结果
     */
    @Operation(summary = "根据ID删除项目数据源", description = "根据提供的项目数据源ID删除项目数据源")
    @PostMapping("/delete/{id}")
    public R<Boolean> removeById(@PathVariable @Parameter(description = "项目数据源ID", required = true) Long id) {
        return R.ok(genProjectDataSourceService.removeById(id));
    }

    /**
     * 根据ID更新项目数据源
     *
     * @param formRequest 项目数据源更新请求参数
     * @return 操作结果
     */
    @Operation(summary = "根据ID更新项目数据源", description = "根据提供的项目数据源更新请求参数更新项目数据源")
    @PostMapping("/update")
     public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody GenProjectDataSourceFormRequest formRequest) {
        return R.ok(genProjectDataSourceService.updateById(formRequest));
    }



    /**
     * 根据ID查询项目数据源
     *
     * @param id 项目数据源ID
     * @return 项目数据源信息
     */
    @Operation(summary = "根据ID查询项目数据源", description = "根据提供的项目数据源ID查询项目数据源信息")
    @GetMapping("/get/{id}")
    public R<GenProjectDataSourceResponse> findById(@PathVariable @Parameter(description = "项目数据源ID", required = true) Long id) {
        return R.ok(genProjectDataSourceService.getById(id));
    }
    /**
     * 分页查询项目数据源
     *
     * @param queryRequest 项目数据源查询请求参数
     * @return 项目数据源分页信息
     */
    @Operation(summary = "分页查询项目数据源", description = "根据提供的查询请求参数分页查询项目数据源信息")
    @GetMapping("/page")
    public R<PageResponse<GenProjectDataSourceResponse>> selectPage(GenProjectDataSourceQueryRequest queryRequest) {
        return R.ok(genProjectDataSourceService.selectPage(queryRequest));
    }


}

