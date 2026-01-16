package com.xht.modules.admin.system.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.utils.tree.INode;
import com.xht.framework.core.validation.Groups;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.framework.oauth2.annotation.IsAdmin;
import com.xht.modules.admin.system.domain.form.SysUserForm;
import com.xht.modules.admin.system.domain.form.UpdatePwdFrom;
import com.xht.modules.admin.system.domain.query.SysUserQuery;
import com.xht.modules.admin.system.domain.response.SysUserResponse;
import com.xht.modules.admin.system.domain.vo.SysUserVo;
import com.xht.modules.admin.system.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理相关的API
 *
 * @author xht
 **/
@RestController
@RequestMapping("/sys/user")
@Tag(name = "用户模块", description = "用户管理相关的API")
@RequiredArgsConstructor
public class SysUserController {

    private final IUserService userService;

    /**
     * 用户添加
     *
     * @param userForm 用户创建请求信息，
     * @return 返回一个R对象，其中包含一个布尔值，表示用户是否创建成功
     */
    @CheckMenu("sys:user:create")
    @PostMapping("/create")
    @Operation(summary = "用户添加", description = "用户添加")
    public R<Void> create(@Valid @RequestBody SysUserForm userForm) {
        userService.create(userForm);
        return R.ok();
    }

    /**
     * 根据ID删除用户
     *
     * @param userId 用户唯一标识符
     * @return 返回一个R对象，其中包含一个布尔值，表示用户是否删除成功
     */
    @CheckMenu("sys:user:remove")
    @PostMapping("/remove/{userId}")
    @Operation(summary = "删除用户", description = "根据ID删除用户")
    public R<Void> removeById(@PathVariable Long userId) {
        userService.removeByUserId(userId);
        return R.ok();
    }

    /**
     * 根据ID更新用户信息
     *
     * @param userForm 包含更新信息的用户请求对象，使用@Valid注解确保请求数据的有效性
     * @return 返回一个R对象，其中包含一个布尔值，表示用户信息是否更新成功
     */
    @CheckMenu("sys:user:update")
    @PostMapping("/update")
    @Operation(summary = "更新用户信息", description = "根据ID更新用户信息")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody SysUserForm userForm) {
        userService.update(userForm);
        return R.ok();
    }

    /**
     * 密码重置
     *
     * @param userId 用户ID
     * @return 返回一个R对象，其中包含一个布尔值，表示密码是否重置成功
     */
    @IsAdmin
    @PostMapping("/reset/{userId}/pwd")
    @Operation(summary = "密码重置", description = "密码重置")
    public R<Void> resetPassword(@Valid @PathVariable Long userId) {
        userService.resetPassword(userId);
        return R.ok();
    }

    /**
     * 密码修改
     *
     * @param form 包含修改密码信息的用户请求对象，使用@Valid注解确保请求数据的有效性
     * @return 返回一个R对象，其中包含一个布尔值，表示密码是否修改成功
     */
    @CheckMenu("sys:user:pwd")
    @PostMapping("/update/pwd")
    @Operation(summary = "密码修改", description = "密码修改")
    public R<Void> updatePassword(@Valid @RequestBody UpdatePwdFrom form) {
        userService.updatePassword(form);
        return R.ok();
    }

    /**
     * 用户状态修改
     *
     * @param userId 用户ID
     * @param status 新的状态
     * @return 返回一个R对象，其中包含一个布尔值，表示状态是否修改成功
     */
    @CheckMenu("sys:user:update")
    @PostMapping("/update/{userId}/{status}")
    @Operation(summary = "用户状态修改", description = "用户状态修改")
    public R<Void> updateStatus(@PathVariable("userId") Long userId, @PathVariable("status") UserStatusEnums status) {
        userService.updateStatus(userId, status);
        return R.ok();
    }

    /**
     * 根据ID获取用户详情
     *
     * @param id 用户唯一标识符
     * @return 返回一个R对象，其中包含SysUserVO对象，表示用户详情信息
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "获取用户详情", description = "根据ID获取用户详情")
    public R<SysUserVo> findById(@PathVariable Long id) {
        return R.ok(userService.findByUserId(id));
    }

    /**
     * 分页获取用户列表
     *
     * @param query 查询请求参数，可以包含分页信息、排序信息或过滤条件等
     * @return 返回一个R对象，其中包含Page<SysUserVO>对象，表示分页用户列表信息
     */
    @GetMapping("/page")
    @Operation(summary = "分页获取用户列表", description = "分页获取用户列表")
    public R<PageResponse<SysUserResponse>> findPageList(SysUserQuery query) {
        return R.ok(userService.findPageList(query));
    }

    /**
     * 获取当前登录用户的信息
     *
     * @return 登录用户信息
     */
    @GetMapping("/profile")
    @Operation(summary = "获取当前登录的用户信息", description = "获取当前登录的用户信息")
    public R<SysUserVo> getUserProfileInfo() {
        return R.ok(userService.getUserProfileInfo());
    }

    /**
     * 获取当前登录用户所拥有的路由
     *
     * @return 当前登录用户所拥有的路由
     */
    @GetMapping("/profile/routers")
    @Operation(summary = "获取当前登录用户所拥有的路由", description = "获取当前登录用户所拥有的路由")
    public R<List<INode<Long>>> getRouters() {
        return R.ok(userService.getRouters());
    }

}
