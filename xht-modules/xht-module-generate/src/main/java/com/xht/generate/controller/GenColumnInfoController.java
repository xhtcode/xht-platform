package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.request.GenColumnInfoFormRequest;
import com.xht.generate.domain.request.GenColumnInfoQueryRequest;
import com.xht.generate.domain.response.GenColumnInfoResponse;
import com.xht.generate.service.IGenColumnInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 字段信息管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "字段信息管理", description = "字段信息管理")
@RestController
@RequestMapping("/gen/column/info")
@RequiredArgsConstructor
public class GenColumnInfoController {

    private final IGenColumnInfoService genColumnInfoService;

    /**
     * 创建字段信息
     *
     * @param formRequest 字段信息表单请求参数
     * @return 操作结果
     */
    @Operation(summary = "创建字段信息", description = "根据提供的请求参数创建一个新的字段信息")
    @PostMapping("/add")
    public R<Boolean> create(@Validated(value = {Groups.Create.class}) @RequestBody GenColumnInfoFormRequest formRequest) {
        return R.ok(genColumnInfoService.create(formRequest));
    }

    /**
     * 根据ID更新字段信息
     *
     * @param formRequest 字段信息更新请求参数
     * @return 操作结果
     */
    @Operation(summary = "根据ID更新字段信息", description = "根据提供的字段信息更新请求参数更新字段信息")
    @PostMapping("/update")
     public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody GenColumnInfoFormRequest formRequest) {
        return R.ok(genColumnInfoService.updateById(formRequest));
    }



    /**
     * 根据ID查询字段信息
     *
     * @param id 字段信息ID
     * @return 字段信息信息
     */
    @Operation(summary = "根据ID查询字段信息", description = "根据提供的字段信息ID查询字段信息信息")
    @GetMapping("/get/{id}")
    public R<GenColumnInfoResponse> findById(@PathVariable @Parameter(description = "字段信息ID", required = true) Long id) {
        return R.ok(genColumnInfoService.getById(id));
    }
    /**
     * 分页查询字段信息
     *
     * @param queryRequest 字段信息查询请求参数
     * @return 字段信息分页信息
     */
    @Operation(summary = "分页查询字段信息", description = "根据提供的查询请求参数分页查询字段信息信息")
    @GetMapping("/page")
    public R<PageResponse<GenColumnInfoResponse>> selectPage(GenColumnInfoQueryRequest queryRequest) {
        return R.ok(genColumnInfoService.selectPage(queryRequest));
    }


}

