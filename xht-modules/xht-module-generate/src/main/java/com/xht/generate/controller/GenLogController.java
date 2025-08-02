package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.request.GenLogFormRequest;
import com.xht.generate.domain.request.GenLogQueryRequest;
import com.xht.generate.domain.response.GenLogResponse;
import com.xht.generate.service.IGenLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 生成日志管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "生成日志管理", description = "生成日志管理")
@RestController
@RequestMapping("/gen/log")
@RequiredArgsConstructor
public class GenLogController {

    private final IGenLogService genLogService;

    /**
     * 创建生成日志
     *
     * @param formRequest 生成日志表单请求参数
     * @return 操作结果
     */
    @Operation(summary = "创建生成日志", description = "根据提供的请求参数创建一个新的生成日志")
    @PostMapping("/add")
    public R<Boolean> create(@Validated(value = {Groups.Create.class}) @RequestBody GenLogFormRequest formRequest) {
        return R.ok(genLogService.create(formRequest));
    }

    /**
     * 根据ID删除生成日志
     *
     * @param id 生成日志ID
     * @return 操作结果
     */
    @Operation(summary = "根据ID删除生成日志", description = "根据提供的生成日志ID删除生成日志")
    @PostMapping("/delete/{id}")
    public R<Boolean> removeById(@PathVariable @Parameter(description = "生成日志ID", required = true) Long id) {
        return R.ok(genLogService.removeById(id));
    }

    /**
     * 根据ID更新生成日志
     *
     * @param formRequest 生成日志更新请求参数
     * @return 操作结果
     */
    @Operation(summary = "根据ID更新生成日志", description = "根据提供的生成日志更新请求参数更新生成日志")
    @PostMapping("/update")
     public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody GenLogFormRequest formRequest) {
        return R.ok(genLogService.updateById(formRequest));
    }



    /**
     * 根据ID查询生成日志
     *
     * @param id 生成日志ID
     * @return 生成日志信息
     */
    @Operation(summary = "根据ID查询生成日志", description = "根据提供的生成日志ID查询生成日志信息")
    @GetMapping("/get/{id}")
    public R<GenLogResponse> findById(@PathVariable @Parameter(description = "生成日志ID", required = true) Long id) {
        return R.ok(genLogService.getById(id));
    }
    /**
     * 分页查询生成日志
     *
     * @param queryRequest 生成日志查询请求参数
     * @return 生成日志分页信息
     */
    @Operation(summary = "分页查询生成日志", description = "根据提供的查询请求参数分页查询生成日志信息")
    @GetMapping("/page")
    public R<PageResponse<GenLogResponse>> selectPage(GenLogQueryRequest queryRequest) {
        return R.ok(genLogService.selectPage(queryRequest));
    }


}

