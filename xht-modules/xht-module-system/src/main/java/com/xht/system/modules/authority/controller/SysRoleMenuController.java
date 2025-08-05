package com.xht.system.modules.authority.controller;

import com.xht.framework.core.domain.R;
import com.xht.system.modules.authority.domain.request.SysRoleMenuBindRequest;
import com.xht.system.modules.authority.service.ISysRoleMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色菜单管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "角色菜单管理", description = "角色菜单管理")
@RestController
@RequestMapping("/sys/role/menu")
@RequiredArgsConstructor
public class SysRoleMenuController {

    private final ISysRoleMenuService sysRoleMenuService;

    /**
     * 角色绑定菜单
     *
     * @param bindRequest 角色菜单绑定请求
     * @return 成功、失败
     */
    @Operation(summary = "角色绑定菜单", description = "角色绑定菜单")
    @PostMapping("/bind")
    public R<Boolean> roleMenuBind(@Valid @RequestBody SysRoleMenuBindRequest bindRequest) {
        return R.ok(sysRoleMenuService.roleMenuBind(bindRequest));
    }


    /**
     * 获取当前角色拥有的菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    @Operation(summary = "获取当前角色拥有的菜单ID列表", description = "获取当前角色拥有的菜单ID列表")
    @GetMapping("/{roleId}")
    public R<List<Long>> selectMenuIdByRoleId(@PathVariable("roleId") String roleId) {
        return R.ok(sysRoleMenuService.selectMenuIdByRoleId(roleId));
    }
}
