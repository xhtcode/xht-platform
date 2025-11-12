package com.xht.system.modules.oauth2.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.framework.web.validation.Groups;
import com.xht.system.modules.oauth2.domain.form.SysOauth2ClientForm;
import com.xht.system.modules.oauth2.domain.query.SysOauth2ClientQuery;
import com.xht.system.modules.oauth2.domain.response.SysOauth2ClientResponse;
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
     * @param form OAuth2客户端信息
     * @return true成功、false失败
     */
    @CheckMenu("sys:oauth2:create")
    @PostMapping("/create")
    @Operation(summary = "创建OAuth2客户端")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysOauth2ClientForm form) {
        sysOauth2ClientService.create(form);
        return R.ok();
    }

    /**
     * 删除OAuth2客户端
     *
     * @param ids OAuth2客户端ID集合
     * @return true成功、false失败
     */
    @CheckMenu("sys:oauth2:remove")
    @PostMapping("/remove")
    @Operation(summary = "删除OAuth2客户端")
    public R<Void> removeById(@RequestBody List<Long> ids) {
        sysOauth2ClientService.removeById(ids);
        return R.ok();
    }

    /**
     * 修改OAuth2客户端
     *
     * @param form OAuth2客户端信息
     * @return true成功、false失败
     */
    @CheckMenu("sys:oauth2:update")
    @PostMapping("/update")
    @Operation(summary = "修改OAuth2客户端")
    public R<Void> updateById(@RequestBody SysOauth2ClientForm form) {
        sysOauth2ClientService.updateById(form);
        return R.ok();
    }

    /**
     * 获取OAuth2客户端详情
     *
     * @param id OAuth2客户端ID
     * @return OAuth2客户端详情
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "获取OAuth2客户端详情")
    public R<SysOauth2ClientResponse> findById(@PathVariable Long id) {
        return R.ok(sysOauth2ClientService.findById(id));
    }

    /**
     * 分页查询OAuth2客户端
     *
     * @param query 查询请求参数
     * @return 分页结果
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询OAuth2客户端")
    public R<PageResponse<SysOauth2ClientResponse>>findPageList(SysOauth2ClientQuery query) {
        return R.ok(sysOauth2ClientService.findPageList(query));
    }

}
