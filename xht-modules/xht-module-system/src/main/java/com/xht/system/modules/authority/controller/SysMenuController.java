package com.xht.system.modules.authority.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.tree.INode;
import com.xht.framework.web.validation.Groups;
import com.xht.system.modules.authority.common.enums.MenuStatusEnums;
import com.xht.system.modules.authority.common.enums.MenuTypeEnums;
import com.xht.system.modules.authority.domain.request.SysMenuFormRequest;
import com.xht.system.modules.authority.domain.request.SysMenuQueryRequest;
import com.xht.system.modules.authority.domain.response.SysMenuResponse;
import com.xht.system.modules.authority.service.ISysMenuService;
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
     * @param formRequest 菜单表单请求参数
     * @return 操作结果
     */
    @Operation(summary = "创建菜单", description = "根据提供的请求参数创建一个新的菜单")
    @PostMapping("/add")
    public R<Boolean> create(@Validated(value = {Groups.Create.class}) @RequestBody SysMenuFormRequest formRequest) {
        return R.ok(sysMenuService.create(formRequest));
    }

    /**
     * 根据ID删除菜单
     *
     * @param id 菜单ID
     * @return 操作结果
     */
    @Operation(summary = "根据ID删除菜单", description = "根据提供的菜单ID删除菜单")
    @PostMapping("/delete/{id}")
    public R<Boolean> removeById(@PathVariable @Parameter(description = "菜单ID", required = true) Long id) {
        return R.ok(sysMenuService.removeById(id));
    }

    /**
     * 根据ID更新菜单
     *
     * @param formRequest 菜单更新请求参数
     * @return 操作结果
     */
    @Operation(summary = "根据ID更新菜单", description = "根据提供的菜单更新请求参数更新菜单")
    @PostMapping("/update")
     public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody SysMenuFormRequest formRequest) {
        return R.ok(sysMenuService.updateById(formRequest));
    }

    /**
     * 修改菜单状态
     *
     * @param id     菜单ID
     * @param status 菜单状态
     * @return 操作结果 成功：true 失败：false
     */
    @Operation(summary = "修改菜单状态", description = "根据提供的菜单ID和状态修改菜单状态")
    @PostMapping("/updateStatus/{id}/{status}")
    public R<Boolean> updateStatus(@PathVariable("id") @Parameter(description = "菜单ID", required = true) Long id,
                                   @PathVariable("status") @Parameter(description = "菜单状态", required = true) MenuStatusEnums status) {
        return R.ok(sysMenuService.updateStatus(id, status));
    }

    /**
     * 根据ID查询菜单
     *
     * @param id 菜单ID
     * @return 菜单信息
     */
    @Operation(summary = "根据ID查询菜单", description = "根据提供的菜单ID查询菜单信息")
    @GetMapping("/get/{id}")
    public R<SysMenuResponse> findById(@PathVariable @Parameter(description = "菜单ID", required = true) Long id) {
        return R.ok(sysMenuService.getById(id));
    }

    /**
     * 查询菜单列表(树形结构)
     *
     * @param queryRequest 菜单查询请求参数
     * @return 菜单树形结构信息
     */
    @Operation(summary = "查询菜单列表(树形结构)", description = "根据提供的查询请求参数查询菜单列表(树形结构)信息")
    @GetMapping("/tree")
    public R<List<INode<Long>>> findTree(SysMenuQueryRequest queryRequest) {
        return R.ok(sysMenuService.findTree(queryRequest));
    }

    /**
     * 根据菜单类型查询菜单列表(树形结构)
     *
     * @param menuType 菜单类型
     * @return 菜单树形结构信息
     */
    @Operation(summary = "根据菜单类型查询菜单列表(树形结构)", description = "根据菜单类型查询菜单列表(树形结构)信息")
    @GetMapping("/tree/{menuType}")
    public R<List<INode<Long>>> findSystemTree(@PathVariable @Parameter(description = "菜单类型", required = true) MenuTypeEnums menuType) {
        return R.ok(sysMenuService.findSystemTree(menuType));
    }
}
