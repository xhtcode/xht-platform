package com.xht.modules.admin.audit.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.modules.admin.audit.domain.query.BLogQuery;
import com.xht.modules.admin.audit.domain.response.BLogResponse;
import com.xht.modules.admin.audit.service.IBLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 系统日志
 *
 * @author xht
 **/
@Tag(name = "系统日志管理")
@RestController
@RequestMapping("/sys/log")
@RequiredArgsConstructor
public class BLogController {

    private final IBLogService bLogService;


    /**
     * 获取系统日志详情
     *
     * @param id 系统日志ID
     * @return 系统日志详情
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "获取系统日志详情")
    public R<BLogResponse> findById(@PathVariable Long id) {
        return R.ok(bLogService.findById(id));
    }

    /**
     * 分页查询系统日志岗位
     *
     * @param query 系统日志岗位查询请求参数
     * @return 系统日志岗位分页信息
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询系统日志岗位", description = "根据提供的查询请求参数分页查询系统日志岗位信息")
    public R<PageResponse<BLogResponse>> findPageList(@Valid BLogQuery query) {
        return R.ok(bLogService.findPageList(query));
    }

}
