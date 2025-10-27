package com.xht.system.modules.dict.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.system.modules.dict.domain.request.SysDictForm;
import com.xht.system.modules.dict.domain.request.SysDictQuery;
import com.xht.system.modules.dict.domain.response.SysDictResp;
import com.xht.system.modules.dict.service.ISysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典类型管理
 *
 * @author xht
 **/
@Tag(name = "字典类型管理")
@RestController
@RequestMapping("/sys/dict/types")
@RequiredArgsConstructor
public class SysDictController {

    private final ISysDictService sysDictService;

    /**
     * 创建字典类型
     *
     * @param form 字典类型信息
     * @return true成功、false失败
     */
    @Operation(summary = "创建字典类型")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysDictForm form) {
        sysDictService.create(form);
        return R.ok();
    }

    /**
     * 删除字典类型
     *
     * @param ids 字典类型ID集合
     * @return true成功、false失败
     */
    @Operation(summary = "删除字典类型")
    @PostMapping("/remove")
    public R<Void> removeById(@RequestBody List<Long> ids) {
        sysDictService.removeById(ids);
        return R.ok();
    }

    /**
     * 修改字典类型
     *
     * @param form 字典类型信息
     * @return true成功、false失败
     */
    @Operation(summary = "修改字典类型")
    @PostMapping("/update")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody SysDictForm form) {
        sysDictService.updateById(form);
        return R.ok();
    }

    /**
     * 获取字典类型详情
     *
     * @param id 字典类型ID
     * @return 字典类型详情
     */
    @Operation(summary = "获取字典类型详情")
    @GetMapping("/get/{id}")
    public R<SysDictResp> findById(@PathVariable Long id) {
        return R.ok(sysDictService.findById(id));
    }

    /**
     * 分页查询字典类型
     *
     * @param query 系统字典查询参数
     * @return 分页结果
     */
    @Operation(summary = "分页查询字典类型")
    @GetMapping("/page")
    public R<PageResponse<SysDictResp>>findPageList(SysDictQuery query) {
        return R.ok(sysDictService.findPageList(query));
    }

}
