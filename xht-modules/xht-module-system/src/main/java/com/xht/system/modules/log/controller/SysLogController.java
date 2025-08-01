package com.xht.system.modules.log.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.openfeign.annotation.NoAuthentication;
import com.xht.system.modules.log.domian.request.SysLogFormRequest;
import com.xht.system.modules.log.domian.request.SysLogQueryRequest;
import com.xht.system.modules.log.domian.response.SysLogResponse;
import com.xht.system.modules.log.service.ISysLogService;
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
public class SysLogController {

    private final ISysLogService sysLogService;


    /**
     * 获取系统日志详情
     *
     * @param id 系统日志ID
     * @return 系统日志详情
     */
    @Operation(summary = "获取系统日志详情")
    @GetMapping("/get/{id}")
    public R<SysLogResponse> getById(@PathVariable Long id) {
        return R.ok(sysLogService.getById(id));
    }

    /**
     * 分页查询系统日志岗位
     *
     * @param queryRequest 系统日志岗位查询请求参数
     * @return 系统日志岗位分页信息
     */
    @Operation(summary = "分页查询系统日志岗位", description = "根据提供的查询请求参数分页查询系统日志岗位信息")
    @GetMapping("/page")
    public R<PageResponse<SysLogResponse>> selectPage(@Valid SysLogQueryRequest queryRequest) {
        return R.ok(sysLogService.selectPage(queryRequest));
    }

}
