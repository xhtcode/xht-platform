package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.request.GenTypeMappingFormRequest;
import com.xht.generate.domain.request.GenTypeMappingQueryRequest;
import com.xht.generate.domain.response.GenTypeMappingResponse;
import com.xht.generate.service.IGenTypeMappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * @param formRequest 字段映射表单请求参数
     * @return 操作结果
     */
    @Operation(summary = "创建字段映射", description = "根据提供的请求参数创建一个新的字段映射")
    @PostMapping("/add")
    public R<Boolean> create(@Validated(value = {Groups.Create.class}) @RequestBody GenTypeMappingFormRequest formRequest) {
        return R.ok(genTypeMappingService.create(formRequest));
    }

    /**
     * 根据ID删除字段映射
     *
     * @param id 字段映射ID
     * @return 操作结果
     */
    @Operation(summary = "根据ID删除字段映射", description = "根据提供的字段映射ID删除字段映射")
    @PostMapping("/delete/{id}")
    public R<Boolean> removeById(@PathVariable @Parameter(description = "字段映射ID", required = true) Long id) {
        return R.ok(genTypeMappingService.removeById(id));
    }

    /**
     * 根据ID更新字段映射
     *
     * @param formRequest 字段映射更新请求参数
     * @return 操作结果
     */
    @Operation(summary = "根据ID更新字段映射", description = "根据提供的字段映射更新请求参数更新字段映射")
    @PostMapping("/update")
     public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody GenTypeMappingFormRequest formRequest) {
        return R.ok(genTypeMappingService.updateById(formRequest));
    }



    /**
     * 根据ID查询字段映射
     *
     * @param id 字段映射ID
     * @return 字段映射信息
     */
    @Operation(summary = "根据ID查询字段映射", description = "根据提供的字段映射ID查询字段映射信息")
    @GetMapping("/get/{id}")
    public R<GenTypeMappingResponse> findById(@PathVariable @Parameter(description = "字段映射ID", required = true) Long id) {
        return R.ok(genTypeMappingService.getById(id));
    }
    /**
     * 分页查询字段映射
     *
     * @param queryRequest 字段映射查询请求参数
     * @return 字段映射分页信息
     */
    @Operation(summary = "分页查询字段映射", description = "根据提供的查询请求参数分页查询字段映射信息")
    @GetMapping("/page")
    public R<PageResponse<GenTypeMappingResponse>> selectPage(GenTypeMappingQueryRequest queryRequest) {
        return R.ok(genTypeMappingService.selectPage(queryRequest));
    }

}

