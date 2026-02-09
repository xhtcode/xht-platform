package com.xht.modules.admin.dict.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.validation.Groups;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.modules.admin.dict.domain.form.SysDictItemForm;
import com.xht.modules.admin.dict.domain.query.SysDictItemQuery;
import com.xht.modules.admin.dict.domain.response.SysDictItemResponse;
import com.xht.modules.admin.dict.service.ISysDictItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典项管理
 *
 * @author xht
 **/
@Tag(name = "字典项管理")
@RestController
@RequestMapping("/sys/dict/item")
@RequiredArgsConstructor
public class SysDictItemController {

    private final ISysDictItemService sysDictItemService;

    /**
     * 创建字典项
     *
     * @param form 字典项创建参数
     * @return true成功、false失败
     */
    @CheckMenu("sys:dict:item:create")
    @PostMapping("/create")
    @Operation(summary = "创建字典项")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysDictItemForm form) {
        sysDictItemService.create(form);
        return R.ok().build();
    }

    /**
     * 删除字典项
     *
     * @param ids 字典项ID
     * @return true成功、false失败
     */
    @CheckMenu("sys:dict:item:remove")
    @PostMapping("/remove")
    @Operation(summary = "删除字典项")
    public R<Void> removeById(@RequestBody List<Long> ids) {
        sysDictItemService.removeById(ids);
        return R.ok().build();
    }

    /**
     * 修改字典项
     *
     * @param form 字典项修改参数
     * @return true成功、false失败
     */
    @CheckMenu("sys:dict:item:update")
    @PostMapping("/update")
    @Operation(summary = "修改字典项")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody SysDictItemForm form) {
        sysDictItemService.updateById(form);
        return R.ok().build();
    }

    /**
     * 获取字典项详情
     *
     * @param id 字典项ID
     * @return 字典项详情
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "获取字典项详情")
    public R<SysDictItemResponse> findById(@PathVariable Long id) {
        return R.ok().build(sysDictItemService.findById(id));
    }

    /**
     * 分页查询字典项
     *
     * @param query 字典项查询参数
     * @return 分页字典项
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询字典项")
    public R<PageResponse<SysDictItemResponse>> page(SysDictItemQuery query) {
        return R.ok().build(sysDictItemService.findPageList(query));
    }

}
