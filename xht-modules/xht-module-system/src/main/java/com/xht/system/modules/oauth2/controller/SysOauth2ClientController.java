package com.xht.system.modules.oauth2.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.security.annotation.InnerAuth;
import com.xht.framework.web.validation.Groups;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientFormRequest;
import com.xht.system.modules.oauth2.domian.request.SysOauth2ClientQueryRequest;
import com.xht.system.modules.oauth2.domian.response.SysOauth2ClientResponse;
import com.xht.system.modules.oauth2.service.ISysOauth2ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OAuth2客户端管理
 *
 * @author xht
 **/
@Tag(name = "OAuth2客户端管理")
@RestController
@RequestMapping("/sys/oauth2/client")
@RequiredArgsConstructor
public class SysOauth2ClientController {

    private final ISysOauth2ClientService sysOauth2ClientService;

    /**
     * 创建OAuth2客户端
     *
     * @param formRequest OAuth2客户端信息
     * @return true成功、false失败
     */
    @Operation(summary = "创建OAuth2客户端")
    @PostMapping("/add")
    public R<Boolean> create(@Validated(value = {Groups.Create.class}) @RequestBody SysOauth2ClientFormRequest formRequest) {
        return R.ok(sysOauth2ClientService.create(formRequest));
    }

    /**
     * 删除OAuth2客户端
     *
     * @param ids OAuth2客户端ID集合
     * @return true成功、false失败
     */
    @Operation(summary = "删除OAuth2客户端")
    @PostMapping("/delete")
    public R<Boolean> removeById(@RequestBody List<Long> ids) {
        return R.ok(sysOauth2ClientService.removeById(ids));
    }

    /**
     * 修改OAuth2客户端
     *
     * @param formRequest OAuth2客户端信息
     * @return true成功、false失败
     */
    @Operation(summary = "修改OAuth2客户端")
    @PostMapping("/update")
    public R<Boolean> updateById(@RequestBody SysOauth2ClientFormRequest formRequest) {
        return R.ok(sysOauth2ClientService.updateById(formRequest));
    }

    /**
     * 获取OAuth2客户端详情
     *
     * @param id OAuth2客户端ID
     * @return OAuth2客户端详情
     */
    @Operation(summary = "获取OAuth2客户端详情")
    @GetMapping("/get/{id}")
    public R<SysOauth2ClientResponse> getById(@PathVariable Long id) {
        return R.ok(sysOauth2ClientService.getById(id));
    }

    /**
     * 分页查询OAuth2客户端
     *
     * @param queryRequest 查询请求参数
     * @return 分页结果
     */
    @Operation(summary = "分页查询OAuth2客户端")
    @GetMapping("/page")
    public R<PageResponse<SysOauth2ClientResponse>> selectPage(SysOauth2ClientQueryRequest queryRequest) {
        return R.ok(sysOauth2ClientService.selectPage(queryRequest));
    }


}
