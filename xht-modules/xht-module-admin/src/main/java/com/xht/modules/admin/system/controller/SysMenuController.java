package com.xht.modules.admin.system.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.tree.INode;
import com.xht.framework.core.validation.Groups;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.modules.admin.system.domain.form.SysMenuForm;
import com.xht.modules.admin.system.domain.query.SysMenuQuery;
import com.xht.modules.admin.system.domain.response.SysMenuResponse;
import com.xht.modules.admin.system.enums.MenuStatusEnums;
import com.xht.modules.admin.system.service.ISysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "菜单管理", description = "菜单管理")
@RestController
@RequestMapping("/sys/menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final ISysMenuService sysMenuService;

    /**
     * 创建菜单
     *
     * @param form 菜单表单请求参数
     * @return 统一响应结果
     */
    @CheckMenu("sys:menu:create")
    @PostMapping("/create")
    @Operation(summary = "创建菜单", description = "根据提供的请求参数创建一个新的菜单")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysMenuForm form) {
        sysMenuService.create(form);
        return R.ok().build();
    }

    /**
     * 根据ID删除菜单
     *
     * @param id 菜单ID
     * @return 统一响应结果
     */
    @CheckMenu("sys:menu:remove")
    @PostMapping("/remove/{id}")
    @Operation(summary = "根据ID删除菜单", description = "根据提供的菜单ID删除菜单")
    public R<Void> removeById(@PathVariable @Parameter(description = "菜单ID", required = true) Long id) {
        sysMenuService.removeById(id);
        return R.ok().build();
    }

    /**
     * 根据ID更新菜单
     *
     * @param form 菜单更新请求参数
     * @return 统一响应结果
     */
    @CheckMenu("sys:menu:update")
    @PostMapping("/update")
    @Operation(summary = "根据ID更新菜单", description = "根据提供的菜单更新请求参数更新菜单")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody SysMenuForm form) {
        sysMenuService.updateById(form);
        return R.ok().build();
    }

    /**
     * 修改菜单状态
     *
     * @param id     菜单ID
     * @param status 菜单状态
     * @return 统一响应结果 成功：true 失败：false
     */
    @CheckMenu("sys:menu:update")
    @PostMapping("/updateStatus/{id}/{status}")
    @Operation(summary = "修改菜单状态", description = "根据提供的菜单ID和状态修改菜单状态")
    public R<Void> updateStatus(@PathVariable("id") @Parameter(description = "菜单ID", required = true) Long id,
                                @PathVariable("status") @Parameter(description = "菜单状态", required = true) MenuStatusEnums status) {
        sysMenuService.updateStatus(id, status);
        return R.ok().build();
    }

    /**
     * 根据ID查询菜单
     *
     * @param id 菜单ID
     * @return 菜单信息
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "根据ID查询菜单", description = "根据提供的菜单ID查询菜单信息")
    public R<SysMenuResponse> findById(@PathVariable @Parameter(description = "菜单ID", required = true) Long id) {
        return R.ok().build(sysMenuService.findById(id));
    }

    /**
     * 查询菜单列表(树形结构)
     *
     * @param query 菜单查询请求参数
     * @return 菜单树形结构信息
     */
    @GetMapping("/tree")
    @Operation(summary = "查询菜单列表(树形结构)", description = "根据提供的查询请求参数查询菜单列表(树形结构)信息")
    public R<List<INode<Long>>> findTree(SysMenuQuery query) {
        return R.ok().build(sysMenuService.findTree(query));
    }

    /**
     * 根据条件查询是否包含菜单类型为button菜单列表(树形结构)
     *
     * @return 菜单树形结构信息
     */
    @GetMapping("/tree/system")
    @Operation(summary = "根据条件查询是否包含菜单类型为button菜单列表(树形结构)", description = "根据条件查询是否包含菜单类型为button菜单列表(树形结构)信息")
    public R<List<INode<Long>>> getMenuTreeSystemTool() {
        return R.ok().build(sysMenuService.getMenuTreeSystemTool());
    }

}
