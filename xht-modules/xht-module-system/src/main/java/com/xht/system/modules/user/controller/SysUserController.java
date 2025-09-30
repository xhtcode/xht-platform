package com.xht.system.modules.user.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.domain.request.SysUserForm;
import com.xht.system.modules.user.domain.request.SysUserQuery;
import com.xht.system.modules.user.domain.request.UpdatePwdFrom;
import com.xht.system.modules.user.domain.vo.SysUserVO;
import com.xht.system.modules.user.service.IUserService;
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
     * 该方法用于根据提供的用户创建请求信息创建一个新用户。
     * 请求体中需要包含创建用户所需的所有必要信息。
     *
     * @param form 用户创建请求信息，使用@Valid注解确保请求数据的有效性
     * @return 返回一个R对象，其中包含一个布尔值，表示用户是否创建成功
     */
    @Operation(summary = "用户添加", description = "用户添加")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody SysUserForm form) {
        userService.create(form);
        return R.ok();
    }

    /**
     * 根据ID删除用户
     * 该方法用于根据用户ID删除一个用户。
     *
     * @param id 用户唯一标识符
     * @return 返回一个R对象，其中包含一个布尔值，表示用户是否删除成功
     */
    @Operation(summary = "删除用户", description = "根据ID删除用户")
    @PostMapping("/remove/{id}")
    public R<Void> removeById(@PathVariable Long id) {
        userService.delete(id);
        return R.ok();
    }

    /**
     * 根据ID批量删除用户
     * 该方法用于根据用户ID删除一个用户。
     *
     * @param ids 用户唯一标识符
     * @return 返回一个R对象，其中包含一个布尔值，表示用户是否删除成功
     */
    @Operation(summary = "批量删除用户", description = "根据ID删除用户")
    @PostMapping("/remove")
    public R<Void> removeByIds(@RequestBody List<Long> ids) {
        userService.removeByIds(ids);
        return R.ok();
    }

    /**
     * 根据ID更新用户信息
     * 该方法用于根据用户ID更新用户信息。
     * 请求体中需要包含更新用户所需的信息。
     *
     * @param form 包含更新信息的用户请求对象，使用@Valid注解确保请求数据的有效性
     * @return 返回一个R对象，其中包含一个布尔值，表示用户信息是否更新成功
     */
    @Operation(summary = "更新用户信息", description = "根据ID更新用户信息")
    @PostMapping("/update")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody SysUserForm form) {
        userService.update(form);
        return R.ok();
    }

    /**
     * 根据ID获取用户详情
     * 该方法用于根据用户ID获取用户的详细信息。
     *
     * @param id 用户唯一标识符
     * @return 返回一个R对象，其中包含SysUserVO对象，表示用户详情信息
     */
    @Operation(summary = "获取用户详情", description = "根据ID获取用户详情")
    @GetMapping("/get/{id}")
    public R<SysUserVO> findById(@PathVariable Long id) {
        return R.ok(userService.findByUserId(id));
    }

    /**
     * 分页获取用户列表
     * 该方法用于分页查询用户列表，可以根据传入的查询请求参数获取相应的用户信息列表。
     *
     * @param query 查询请求参数，可以包含分页信息、排序信息或过滤条件等
     * @return 返回一个R对象，其中包含Page<SysUserVO>对象，表示分页用户列表信息
     */
    @Operation(summary = "分页获取用户列表", description = "分页获取用户列表")
    @GetMapping("/page")
    public R<PageResponse<SysUserVO>> pageList(SysUserQuery query) {
        return R.ok(userService.pageList(query));
    }

    /**
     * 密码重置
     * 该方法用于根据提供的用户更新请求信息重置用户的密码。
     * 请求体中需要包含用户的ID和新的密码信息。
     *
     * @param userId 用户ID
     * @return 返回一个R对象，其中包含一个布尔值，表示密码是否重置成功
     */
    @Operation(summary = "密码重置", description = "密码重置")
    @PostMapping("/reset/{userId}/pwd")
    public R<Void> resetPassword(@Valid @PathVariable Long userId) {
        userService.resetPassword(userId);
        return R.ok();
    }

    /**
     * 密码修改
     * 该方法用于根据提供的用户更新请求信息修改用户的密码。
     * 请求体中需要包含用户的ID和旧密码、新密码信息。
     *
     * @param form 包含修改密码信息的用户请求对象，使用@Valid注解确保请求数据的有效性
     * @return 返回一个R对象，其中包含一个布尔值，表示密码是否修改成功
     */
    @Operation(summary = "密码修改", description = "密码修改")
    @PostMapping("/update/pwd")
    public R<Void> updatePassword(@Valid @RequestBody UpdatePwdFrom form) {
        userService.updatePassword(form);
        return R.ok();
    }

    /**
     * 用户状态修改
     * 该方法用于根据提供的用户更新请求信息修改用户的状态。`
     * 请求体中需要包含用户的ID和新的状态信息。
     *
     * @param userId 用户ID
     * @param status 新的状态
     * @return 返回一个R对象，其中包含一个布尔值，表示状态是否修改成功
     */
    @Operation(summary = "用户状态修改", description = "用户状态修改")
    @PostMapping("/update/{userId}/{status}")
    public R<Void> updateStatus(@PathVariable("userId") Long userId, @PathVariable("status") UserStatusEnums status) {
        userService.updateStatus(userId, status);
        return R.ok();
    }


}
