package com.xht.system.modules.authority.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.system.modules.authority.common.enums.RoleStatusEnums;
import com.xht.system.modules.authority.domain.form.SysRoleBasicForm;
import com.xht.system.modules.authority.domain.query.SysRoleBasicQuery;
import com.xht.system.modules.authority.domain.response.SysRoleResponse;
import com.xht.system.modules.authority.service.ISysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "角色管理", description = "角色管理相关的API")
@RestController
@RequestMapping("/sys/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final ISysRoleService sysRoleService;

    /**
     * 创建角色
     *
     * @param form 角色表单请求参数
     * @return 统一响应结果
     */
    @Operation(summary = "创建角色", description = "根据提供的请求参数创建一个新的角色")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysRoleBasicForm form) {
        sysRoleService.create(form);
        return R.ok();
    }

    /**
     * 根据ID删除角色
     *
     * @param id 角色ID
     * @return 统一响应结果
     */
    @Operation(summary = "根据ID删除角色", description = "根据提供的角色ID删除角色")
    @PostMapping("/remove/{id}")
    public R<Void> removeById(@PathVariable Long id) {
        sysRoleService.removeById(id);
        return R.ok();
    }

    /**
     * 根据ID删除角色
     *
     * @param ids 角色ID
     * @return 统一响应结果
     */
    @Operation(summary = "根据ID删除角色", description = "根据提供的角色ID删除角色")
    @PostMapping("/remove/")
    public R<Void> removeByIds(@RequestBody List<Long> ids) {
        sysRoleService.removeByIds(ids);
        return R.ok();
    }

    /**
     * 根据ID更新角色
     *
     * @param form 角色更新请求参数
     * @return 统一响应结果
     */
    @Operation(summary = "根据ID更新角色", description = "根据提供的角色更新请求参数更新角色")
    @PostMapping("/update")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody SysRoleBasicForm form) {
        sysRoleService.updateById(form);
        return R.ok();
    }

    /**
     * 修改角色状态
     *
     * @param id     角色ID
     * @param status 角色状态
     * @return 统一响应结果 成功：true 失败：false
     */
    @Operation(summary = "修改角色状态", description = "根据提供的角色ID和状态修改角色状态")
    @PostMapping("/updateStatus/{id}/{status}")
    public R<Void> updateStatus(@PathVariable("id") @Parameter(description = "角色ID", required = true) Long id,
                                   @PathVariable("status") @Parameter(description = "角色状态", required = true) RoleStatusEnums status) {
        sysRoleService.updateStatus(id, status);
        return R.ok();
    }

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return 角色信息
     */
    @Operation(summary = "根据ID查询角色", description = "根据提供的角色ID查询角色信息")
    @GetMapping("/get/{id}")
    public R<SysRoleResponse> findById(@PathVariable @Parameter(description = "角色ID", required = true) Long id) {
        return R.ok(sysRoleService.findById(id));
    }

    /**
     * 分页查询角色
     *
     * @param query 角色查询请求参数
     * @return 角色分页信息
     */
    @Operation(summary = "分页查询角色", description = "根据提供的查询请求参数分页查询角色信息")
    @GetMapping("/page")
    public R<PageResponse<SysRoleResponse>>findPageList(SysRoleBasicQuery query) {
        return R.ok(sysRoleService.findPageList(query));
    }

    /**
     * 查询全部角色
     *
     * @return 全部角色
     */
    @Operation(summary = "查询全部角色", description = "查询全部角色")
    @GetMapping("/list")
    public R<List<SysRoleResponse>> list() {
        return R.ok(sysRoleService.list());
    }
}
