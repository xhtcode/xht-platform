package com.xht.modules.admin.area.controller;

import com.xht.framework.core.domain.R;
import com.xht.modules.admin.area.domain.form.SysAreaForm;
import com.xht.modules.admin.area.domain.response.SysAreaResponse;
import com.xht.modules.admin.area.service.ISysAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
     *
     */
    @Operation(summary = "添加系统管理-行政区划")
    @PostMapping("/create")
    public R<Void> create(@RequestBody SysAreaForm form) {
        sysAreaService.create(form);
        return R.ok();
    }

    /**
     * 删除系统管理-行政区划
     *
     * @param cityCode 系统管理-行政区划ID
     *
     */
    @Operation(summary = "删除系统管理-行政区划")
    @PostMapping("/remove/{id}")
    public R<Void> removeById(@PathVariable("id") String cityCode) {
        sysAreaService.removeById(cityCode);
        return R.ok();
    }

    /**
     * 修改系统管理-行政区划
     *
     * @param form 系统管理-行政区划
     *
     */
    @Operation(summary = " 修改系统管理-行政区划")
    @PostMapping("/update")
    public R<Void> updateById(@RequestBody SysAreaForm form) {
        sysAreaService.updateById(form);
        return R.ok();
    }

    /**
     * 查询系统管理-行政区划
     *
     * @param cityCode 系统管理-行政区划ID
     * @return 系统管理-行政区划
     */
    @Operation(summary = "查询系统管理-行政区划")
    @PostMapping("/get/{id}")
    public R<SysAreaResponse> findById(@PathVariable("id") String cityCode) {
        return R.ok(sysAreaService.findById(cityCode));
    }

    /**
     * 查询系统管理-行政区划列表
     *
     * @param parentCode 上级系统管理-行政区划ID
     * @return 系统管理-行政区划列表
     */
    @Operation(summary = "查询系统管理-行政区划列表")
    @PostMapping("/list")
    public R<List<SysAreaResponse>> findListByParentCode(@RequestParam(required = false, defaultValue = "1") String parentCode) {
        return R.ok(sysAreaService.findListByParentCode(parentCode));
    }

}
