package com.xht.platform.controller.area;

import com.xht.framework.common.domain.R;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.framework.validation.Groups;
import com.xht.platform.area.domain.form.SysAreaForm;
import com.xht.platform.area.domain.response.SysAreaResponse;
import com.xht.platform.area.service.ISysAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述 ：系统管理-行政区划 控制器
 *
 * @author xht
 **/
@Tag(name = "行政区划")
@RestController
@RequestMapping("/sys/area")
@RequiredArgsConstructor
public class SysAreaController {

    private final ISysAreaService sysAreaService;

    /**
     * 添加系统管理-行政区划
     *
     * @param form 系统管理-行政区划
     */
    @BLog(value = "行政区划", description = "添加系统管理-行政区划")
    @Operation(summary = "添加系统管理-行政区划")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysAreaForm form) {
        sysAreaService.create(form);
        return R.ok().build();
    }

    /**
     * 根据主键`id`删除系统管理-行政区划
     *
     * @param areaId 系统管理-字典表主键
     * @return 统一响应结果
     */
    @BLog(value = "行政区划", description = "根据主键`id`删除系统管理-行政区划")
    @CheckMenu("sys:area:remove")
    @Operation(summary = "根据主键`id`删除系统管理-行政区划")
    @PostMapping("/remove/{areaId}")
    public R<Void> remove(@PathVariable Long areaId) {
        sysAreaService.remove(areaId);
        return R.ok().build();
    }

    /**
     * 修改系统管理-行政区划
     *
     * @param areaId   系统管理-行政区划主键
     * @param form 系统管理-行政区划
     */
    @BLog(value = "行政区划", description = "修改系统管理-行政区划")
    @Operation(summary = " 修改系统管理-行政区划")
    @PostMapping("/update/{areaId}")
    public R<Void> updateById(@PathVariable Long areaId, @Validated(value = {Groups.Update.class}) @RequestBody SysAreaForm form) {
        sysAreaService.updateById(areaId, form);
        return R.ok().build();
    }

    /**
     * 根据主键`id`查询系统管理-行政区划
     *
     * @param areaId 系统管理-行政区划主键
     * @return 系统管理-行政区划信息
     */
    @Operation(summary = "根据主键`id`查询系统管理-行政区划")
    @GetMapping("/get/{areaId}")
    public R<SysAreaResponse> findById(@PathVariable Long areaId) {
        return R.ok().build(sysAreaService.findById(areaId));
    }

    /**
     * 查询系统管理-行政区划列表
     *
     * @param parentId 上级系统管理-行政区划ID
     * @return 系统管理-行政区划列表
     */
    @IgnoreAuth(aop = false)
    @Operation(summary = "根据上级区划id查询系统管理-行政区划列表")
    @GetMapping("/list/{parentId}")
    public R<List<SysAreaResponse>> listByParentId(@PathVariable Long parentId) {
        return R.ok().build(sysAreaService.listByParentId(parentId));
    }

}
