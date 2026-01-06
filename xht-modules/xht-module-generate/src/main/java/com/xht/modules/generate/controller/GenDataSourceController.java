package com.xht.modules.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.validation.Groups;
import com.xht.modules.generate.domain.form.GenDataSourceForm;
import com.xht.modules.generate.domain.query.GenDataSourceQuery;
import com.xht.modules.generate.domain.response.GenDataSourceResponse;
import com.xht.modules.generate.service.IGenDataSourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "数据源管理", description = "数据源管理")
@RestController
@RequestMapping("/gen/datasource")
@RequiredArgsConstructor
public class GenDataSourceController {

    private final IGenDataSourceService genDataSourceService;

    /**
     * 创建数据源
     *
     * @param form 数据源表单请求参数
     * @return 统一响应结果
     */
    @Operation(summary = "创建数据源", description = "根据提供的请求参数创建一个新的数据源")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody GenDataSourceForm form) {
        genDataSourceService.create(form);
        return R.ok();
    }

    /**
     * 根据ID删除数据源
     *
     * @param id 数据源ID
     * @return 统一响应结果
     */
    @Operation(summary = "根据ID删除数据源", description = "根据提供的数据源ID删除数据源")
    @PostMapping("/remove/{id}")
    public R<Void> removeById(@PathVariable @Parameter(description = "数据源ID", required = true) Long id) {
        genDataSourceService.removeById(id);
        return R.ok();
    }

    /**
     * 根据ID更新数据源
     *
     * @param form 数据源更新请求参数
     * @return 统一响应结果
     */
    @Operation(summary = "根据ID更新数据源", description = "根据提供的数据源更新请求参数更新数据源")
    @PostMapping("/update")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody GenDataSourceForm form) {
        genDataSourceService.updateById(form);
        return R.ok();
    }

    /**
     * 根据ID查询数据源
     *
     * @param id 数据源ID
     * @return 数据源信息
     */
    @Operation(summary = "根据ID查询数据源", description = "根据提供的数据源ID查询数据源信息")
    @GetMapping("/get/{id}")
    public R<GenDataSourceResponse> findById(@PathVariable @Parameter(description = "数据源ID", required = true) Long id) {
        return R.ok(genDataSourceService.findById(id));
    }

    /**
     * 按条件查询数据源
     *
     * @param query 数据源查询请求参数
     * @return 数据源分页信息
     */
    @Operation(summary = "按条件查询数据源", description = "根据提供的查询请求参数按条件查询数据源信息")
    @GetMapping("/list")
    public R<List<GenDataSourceResponse>> findList(GenDataSourceQuery query) {
        return R.ok(genDataSourceService.findList(query));
    }

    /**
     * 测试链接
     *
     * @param id 数据源ID
     * @return 测试结果 true:成功 false:失败
     */
    @Operation(summary = "测试链接", description = "测试链接")
    @GetMapping("/connection/{id}")
    public R<Boolean> connection(@PathVariable("id") Long id) {
        return R.ok(genDataSourceService.connection(id));
    }

}

