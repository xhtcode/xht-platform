package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.generate.domain.query.GenLogQueryRequest;
import com.xht.generate.domain.response.GenLogResponse;
import com.xht.generate.service.IGenLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

