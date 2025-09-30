package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.form.GenTypeMappingForm;
import com.xht.generate.domain.query.GenTypeMappingQuery;
import com.xht.generate.domain.response.GenTypeMappingResp;
import com.xht.generate.service.IGenTypeMappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字段映射管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "字段映射管理", description = "字段映射管理")
@RestController
@RequestMapping("/gen/type/mapping")
@RequiredArgsConstructor
public class GenTypeMappingController {

    private final IGenTypeMappingService genTypeMappingService;

    /**
     * 创建字段映射
     *
     * @param form 字段映射表单请求参数
     * @return 统一响应结果
     */
    @Operation(summary = "创建字段映射", description = "根据提供的请求参数创建一个新的字段映射")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody GenTypeMappingForm form) {
        genTypeMappingService.create(form);
        return R.ok();
    }

    /**
     * 根据ID删除字段映射
     *
     * @param id 字段映射ID
     * @return 统一响应结果
     */
    @Operation(summary = "根据ID删除字段映射", description = "根据提供的字段映射ID删除字段映射")
    @PostMapping("/remove/{id}")
    public R<Void> removeById(@PathVariable @Parameter(description = "字段映射ID", required = true) Long id) {
        genTypeMappingService.removeById(id);
        return R.ok();
    }

    /**
     * 根据ID更新字段映射
     *
     * @param form 字段映射更新请求参数
     * @return 统一响应结果
     */
    @Operation(summary = "根据ID更新字段映射", description = "根据提供的字段映射更新请求参数更新字段映射")
    @PostMapping("/update")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody GenTypeMappingForm form) {
        genTypeMappingService.updateById(form);
        return R.ok();
    }

    /**
     * 根据ID查询字段映射
     *
     * @param id 字段映射ID
     * @return 字段映射信息
     */
    @Operation(summary = "根据ID查询字段映射", description = "根据提供的字段映射ID查询字段映射信息")
    @GetMapping("/get/{id}")
    public R<GenTypeMappingResp> findById(@PathVariable @Parameter(description = "字段映射ID", required = true) Long id) {
        return R.ok(genTypeMappingService.findById(id));
    }

    /**
     * 分页查询字段映射
     *
     * @param query 字段映射查询请求参数
     * @return 字段映射分页信息
     */
    @Operation(summary = "分页查询字段映射", description = "根据提供的查询请求参数分页查询字段映射信息")
    @GetMapping("/page")
    public R<PageResponse<GenTypeMappingResp>> pageList(GenTypeMappingQuery query) {
        return R.ok(genTypeMappingService.pageList(query));
    }

    /**
     * 根据数据库类型和目标编程语言类型查询所有的映射关系
     *
     * @param query 字段映射查询请求参数
     * @return 字段映射信息
     */
    @Operation(summary = "根据数据库类型和目标编程语言类型查询所有的映射关系", description = "根据提供的数据库类型和目标编程语言类型查询所有的映射关系")
    @GetMapping("/list")
    public R<List<GenTypeMappingResp>> findAll(@Validated GenTypeMappingQuery query) {
        return R.ok(genTypeMappingService.findAll(query));
    }

}

