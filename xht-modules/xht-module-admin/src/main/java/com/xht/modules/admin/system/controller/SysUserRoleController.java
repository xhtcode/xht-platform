package com.xht.modules.admin.system.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.modules.admin.system.domain.form.UserBindRoleForm;
import com.xht.modules.admin.system.service.IUserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户角色管理
 *
 * @author xht
 **/
@Tag(name = "用户角色管理")
@Slf4j
@RestController
@RequestMapping("/sys/user/role")
@RequiredArgsConstructor
public class SysUserRoleController {

    private final IUserRoleService userRoleService;

    /**
     * 绑定角色
     *
     * @param bindRequest 绑定请求
     * @return 成功或失败
     */
    @CheckMenu("sys:user:role:bind")
    @PostMapping("/bind")
    @Operation(description = "绑定角色", summary = "绑定角色")
    public R<Void> userBindRole(@Valid @RequestBody UserBindRoleForm bindRequest) {
        userRoleService.userBindRole(bindRequest.getUserId(), bindRequest.getRoleIds());
        return R.ok();
    }

    /**
     * 获取当前用户拥有的角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    @GetMapping("/{userId}")
    @Operation(summary = "获取当前用户拥有的角色ID列表", description = "获取当前用户拥有的角色ID列表")
    public R<List<Long>> selectRoleIdByUserId(@PathVariable("userId") String userId) {
        return R.ok(userRoleService.selectRoleIdByUserId(userId));
    }

}
