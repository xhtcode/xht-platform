package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.request.GenTableInfoFormRequest;
import com.xht.generate.domain.request.GenTableInfoQueryRequest;
import com.xht.generate.domain.response.GenTableInfoResponse;
import com.xht.generate.service.IGenTableInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 表信息管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "表信息管理", description = "表信息管理")
@RestController
@RequestMapping("/gen/table/info")
@RequiredArgsConstructor
public class GenTableInfoController {

    private final IGenTableInfoService genTableInfoService;

    /**
     * 导入表
     *
     * @param formRequest 表信息表单请求参数
     * @return 操作结果
     */
    @Operation(summary = "创建表信息")
    @PostMapping("/import")
    public R<Boolean> importTable(@Validated(value = {Groups.Create.class}) @RequestBody GenTableInfoFormRequest formRequest) {
        return R.ok(genTableInfoService.importTable(formRequest));
    }

    /**
     * 根据ID删除表信息
     *
     * @param id 表信息ID
     * @return 操作结果
     */
    @Operation(summary = "根据ID删除表信息")
    @PostMapping("/delete/{id}")
    public R<Boolean> removeById(@PathVariable @Parameter(description = "表信息ID", required = true) Long id) {
        return R.ok(genTableInfoService.removeById(id));
    }

    /**
     * 根据ID更新表信息
     *
     * @param formRequest 表信息更新请求参数
     * @return 操作结果
     */
    @Operation(summary = "根据ID更新表信息")
    @PostMapping("/update")
     public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody GenTableInfoFormRequest formRequest) {
        return R.ok(genTableInfoService.updateById(formRequest));
    }

    /**
     * 根据ID查询表信息
     *
     * @param id 表信息ID
     * @return 表信息信息
     */
    @Operation(summary = "根据ID查询表信息")
    @GetMapping("/get/{id}")
    public R<GenTableInfoResponse> findById(@PathVariable @Parameter(description = "表信息ID", required = true) Long id) {
        return R.ok(genTableInfoService.getById(id));
    }

    /**
     * 分页查询表信息
     *
     * @param queryRequest 表信息查询请求参数
     * @return 表信息分页信息
     */
    @Operation(summary = "分页查询表信息")
    @GetMapping("/page")
    public R<PageResponse<GenTableInfoResponse>> selectPage(GenTableInfoQueryRequest queryRequest) {
        return R.ok(genTableInfoService.selectPage(queryRequest));
    }


}

