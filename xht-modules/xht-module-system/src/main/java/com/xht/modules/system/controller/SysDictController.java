package com.xht.modules.system.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.framework.core.validation.Groups;
import com.xht.api.system.domain.form.SysDictForm;
import com.xht.api.system.domain.query.SysDictQuery;
import com.xht.api.system.domain.response.SysDictResponse;
import com.xht.modules.system.service.ISysDictService;
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
    @CheckMenu("sys:dict:create")
    @PostMapping("/create")
    @Operation(summary = "创建字典类型")

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
    @CheckMenu("sys:dict:remove")
    @PostMapping("/remove")
    @Operation(summary = "删除字典类型")
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
    @CheckMenu("sys:dict:update")
    @PostMapping("/update")
    @Operation(summary = "修改字典类型")
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
    @GetMapping("/get/{id}")
    @Operation(summary = "获取字典类型详情")
    public R<SysDictResponse> findById(@PathVariable Long id) {
        return R.ok(sysDictService.findById(id));
    }

    /**
     * 分页查询字典类型
     *
     * @param query 系统字典查询参数
     * @return 分页结果
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询字典类型")
    public R<PageResponse<SysDictResponse>> findPageList(SysDictQuery query) {
        return R.ok(sysDictService.findPageList(query));
    }


    /**
     * 获取所有字典类型
     *
     * @return 字典类型列表
     */
    @GetMapping("/all")
    @Operation(summary = "获取所有字典类型")
    public R<List<SysDictResponse>> findAll(){
    	return R.ok(sysDictService.findAll());
    }

}
