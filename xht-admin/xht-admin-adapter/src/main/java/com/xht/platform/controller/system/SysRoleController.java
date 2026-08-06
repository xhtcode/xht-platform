package com.xht.platform.controller.system;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.framework.validation.Groups;
import  com.xht.platform.system.domain.form.SysRoleForm;
import  com.xht.platform.system.domain.form.SysRoleMenuBindForm;
import  com.xht.platform.system.domain.query.SysRoleQuery;
import  com.xht.platform.system.domain.response.RoleSelectedMenuResponse;
import  com.xht.platform.system.domain.response.SysRoleResponse;
import  com.xht.platform.system.enums.RoleStatusEnum;
import  com.xht.platform.system.service.ISysRoleMenuService;
import  com.xht.platform.system.service.ISysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    private final ISysRoleMenuService sysRoleMenuService;

    /**
     * 创建角色
     *
     * @param form 角色表单请求参数
     * @return 统一响应结果
     */
    @BLog(value = "角色管理", description = "创建角色")
    @CheckMenu("sys:role:create")
    @Operation(summary = "创建角色", description = "根据提供的请求参数创建一个新的角色")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysRoleForm form) {
        sysRoleService.create(form);
        return R.ok().build();
    }

    /**
     * 根据ID删除角色
     *
     * @param roleId 角色ID
     * @return 统一响应结果
     */
    @BLog(value = "角色管理", description = "删除角色")
    @CheckMenu("sys:role:remove")
    @Operation(summary = "删除角色", description = "根据ID删除角色")
    @PostMapping("/remove/{roleId}")
    public R<Void> remove(@PathVariable Long roleId) {
        sysRoleService.removeByIds(roleId);
        return R.ok().build();
    }

    /**
     * 根据ID更新角色
     *
     * @param form 角色更新请求参数
     * @return 统一响应结果
     */
    @BLog(value = "角色管理", description = "更新角色")
    @CheckMenu("sys:role:updateById")
    @Operation(summary = "更新角色", description = "根据ID更新角色")
    @PostMapping("/update/{roleId}")
    public R<Void> updateById(@PathVariable Long roleId, @Validated(value = {Groups.Update.class}) @RequestBody SysRoleForm form) {
        sysRoleService.updateById(roleId, form);
        return R.ok().build();
    }

    /**
     * 修改角色状态
     *
     * @param roleId     角色ID
     * @param roleStatus 角色状态
     * @return 统一响应结果 成功：true 失败：false
     */
    @BLog(value = "角色管理", description = "修改角色状态")
    @CheckMenu("sys:role:updateById")
    @Operation(summary = "修改角色状态", description = "根据提供的角色ID和状态修改角色状态")
    @PostMapping("/updateStatus/{roleId}/{roleStatus}")
    public R<Void> updateStatus(@PathVariable @Parameter(description = "角色ID", required = true) Long roleId,
                                @PathVariable @Parameter(description = "角色状态", required = true) RoleStatusEnum roleStatus) {
        sysRoleService.updateStatus(roleId, roleStatus);
        return R.ok().build();
    }

    /**
     * 根据ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    @Operation(summary = "查询详情", description = "根据提供的角色ID查询角色信息")
    @GetMapping("/get/{roleId}")
    public R<SysRoleResponse> findById(@PathVariable @Parameter(description = "角色ID", required = true) Long roleId) {
        return R.ok().build(sysRoleService.findById(roleId));
    }

    /**
     * 分页查询角色
     *
     * @param query 角色查询请求参数
     * @return 角色分页信息
     */
    @Operation(summary = "分页查询", description = "根据提供的查询请求参数分页查询角色信息")
    @GetMapping("/page")
    public R<PageResponse<SysRoleResponse>> findPageList(SysRoleQuery query) {
        return R.ok().build(sysRoleService.findPageList(query));
    }

    /**
     * 查询全部角色
     *
     * @return 全部角色
     */
    @Operation(summary = "查询全部", description = "查询全部角色")
    @GetMapping("/list")
    public R<List<SysRoleResponse>> list() {
        return R.ok().build(sysRoleService.list());
    }

    /**
     * 角色绑定菜单
     *
     * @param bindRequest 角色菜单绑定请求
     * @return 成功、失败
     */
    @BLog(value = "角色管理", description = "角色绑定菜单")
    @CheckMenu("sys:role:menu:bind")
    @Operation(summary = "角色绑定菜单", description = "角色绑定菜单")
    @PostMapping("/menu/bind")
    public R<Void> roleMenuBind(@Valid @RequestBody SysRoleMenuBindForm bindRequest) {
        sysRoleMenuService.roleMenuBind(bindRequest);
        return R.ok().build();
    }

    /**
     * 获取当前角色拥有的菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    @Operation(summary = "角色拥有的菜单", description = "获取当前角色拥有的菜单ID列表")
    @GetMapping("/select/menu/{roleId}")
    public R<RoleSelectedMenuResponse> selectMenuIdByRoleId(@PathVariable String roleId) {
        return R.ok().build(sysRoleMenuService.selectMenuIdByRoleId(roleId));
    }

}
