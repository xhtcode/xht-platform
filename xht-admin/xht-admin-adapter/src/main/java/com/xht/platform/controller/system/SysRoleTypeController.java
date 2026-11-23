package com.xht.platform.controller.system;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.framework.validation.Groups;
import com.xht.platform.system.domain.form.SysRoleTypeForm;
import com.xht.platform.system.domain.form.SysRoleTypeMenuBindForm;
import com.xht.platform.system.domain.query.SysRoleTypeQuery;
import com.xht.platform.system.domain.response.RoleSelectedMenuResponse;
import com.xht.platform.system.domain.response.SysRoleTypeResponse;
import com.xht.platform.system.service.ISysRoleTypeService;
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
 * 角色类型管理
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "角色类型管理", description = "角色类型管理相关的API")
@RestController
@RequestMapping("/sys/roleType")
@RequiredArgsConstructor
public class SysRoleTypeController {

    private final ISysRoleTypeService sysRoleTypeService;

    /**
     * 创建角色类型
     *
     * @param form 角色类型表单请求参数
     * @return 统一响应结果
     */
    @BLog(value = "角色类型管理", description = "创建角色类型")
    @CheckMenu("sys:roleType:create")
    @Operation(summary = "创建角色类型", description = "根据提供的请求参数创建一个新的角色类型")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysRoleTypeForm form) {
        sysRoleTypeService.create(form);
        return R.ok().build();
    }

    /**
     * 根据ID删除角色类型
     *
     * @param id 角色类型ID
     * @return 统一响应结果
     */
    @BLog(value = "角色类型管理", description = "删除角色类型")
    @CheckMenu("sys:roleType:remove")
    @Operation(summary = "删除角色类型", description = "根据ID删除角色类型")
    @PostMapping("/remove/{id}")
    public R<Void> remove(@PathVariable Long id) {
        sysRoleTypeService.removeById(id);
        return R.ok().build();
    }

    /**
     * 根据ID更新角色类型
     *
     * @param form 角色类型更新请求参数
     * @return 统一响应结果
     */
    @BLog(value = "角色类型管理", description = "更新角色类型")
    @CheckMenu("sys:roleType:updateById")
    @Operation(summary = "更新角色类型", description = "根据ID更新角色类型")
    @PostMapping("/update/{id}")
    public R<Void> updateById(@PathVariable Long id, @Validated(value = {Groups.Update.class}) @RequestBody SysRoleTypeForm form) {
        sysRoleTypeService.updateById(id, form);
        return R.ok().build();
    }

    /**
     * 根据ID查询角色类型
     *
     * @param id 角色类型ID
     * @return 角色类型信息
     */
    @Operation(summary = "查询详情", description = "根据提供的角色类型ID查询角色类型信息")
    @GetMapping("/get/{id}")
    public R<SysRoleTypeResponse> findById(@PathVariable @Parameter(description = "角色类型ID", required = true) Long id) {
        return R.ok().build(sysRoleTypeService.findById(id));
    }

    /**
     * 分页查询角色类型
     *
     * @param query 角色类型查询请求参数
     * @return 角色类型分页信息
     */
    @Operation(summary = "分页查询", description = "根据提供的查询请求参数分页查询角色类型信息")
    @PostMapping("/page")
    public R<PageResponse<SysRoleTypeResponse>> findPageList(@RequestBody SysRoleTypeQuery query) {
        return R.ok().build(sysRoleTypeService.findPageList(query));
    }

    /**
     * 查询全部角色类型
     *
     * @return 全部角色类型
     */
    @Operation(summary = "查询全部", description = "查询全部角色类型")
    @GetMapping("/list")
    public R<List<SysRoleTypeResponse>> list() {
        return R.ok().build(sysRoleTypeService.list());
    }

    /**
     * 角色类型绑定菜单
     *
     * @param bindRequest 角色类型菜单绑定请求
     * @return 成功、失败
     */
    @BLog(value = "角色类型管理", description = "角色类型绑定菜单")
    @CheckMenu("sys:roleType:menu:bind")
    @Operation(summary = "角色类型绑定菜单", description = "角色类型绑定菜单")
    @PostMapping("/menu/bind")
    public R<Void> roleTypeMenuBind(@Valid @RequestBody SysRoleTypeMenuBindForm bindRequest) {
        sysRoleTypeService.roleTypeMenuBind(bindRequest);
        return R.ok().build();
    }

    /**
     * 获取当前角色类型拥有的菜单ID列表
     *
     * @param roleTypeId 角色类型ID
     * @return 菜单ID列表
     */
    @Operation(summary = "角色类型拥有的菜单", description = "获取当前角色类型拥有的菜单ID列表")
    @GetMapping("/select/menu/{roleTypeId}")
    public R<RoleSelectedMenuResponse> selectMenuIdByTypeId(@PathVariable String roleTypeId) {
        return R.ok().build(sysRoleTypeService.selectMenuIdByTypeId(roleTypeId));
    }

}
