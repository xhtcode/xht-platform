package com.xht.platform.controller.dict;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.framework.validation.Groups;
import  com.xht.platform.dict.domain.form.SysDictItemForm;
import  com.xht.platform.dict.domain.query.SysDictItemQuery;
import  com.xht.platform.dict.domain.response.SysDictItemResponse;
import  com.xht.platform.dict.service.ISysDictItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @BLog(value = "字典管理", description = "创建字典项")
    @CheckMenu("sys:dict:item:create")
    @Operation(summary = "创建字典项")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysDictItemForm form) {
        sysDictItemService.create(form);
        return R.ok().build();
    }

    /**
     * 删除字典项
     *
     * @param dictItemId 字典项ID
     * @return true成功、false失败
     */
    @BLog(value = "字典管理", description = "删除字典项")
    @CheckMenu("sys:dict:item:remove")
    @Operation(summary = "删除字典项")
    @PostMapping("/remove/{dictItemId}")
    public R<Void> remove(@PathVariable Long dictItemId) {
        sysDictItemService.removeById(dictItemId);
        return R.ok().build();
    }

    /**
     * 修改字典项
     *
     * @param dictItemId 字典项ID
     * @param form 字典项修改参数
     * @return true成功、false失败
     */
    @BLog(value = "字典管理", description = "修改字典项")
    @CheckMenu("sys:dict:item:updateById")
    @Operation(summary = "修改字典项")
    @PostMapping("/update/{dictItemId}")
    public R<Void> updateById(@PathVariable Long dictItemId, @Validated(value = {Groups.Update.class}) @RequestBody SysDictItemForm form) {
        sysDictItemService.updateById(dictItemId, form);
        return R.ok().build();
    }

    /**
     * 获取字典项详情
     *
     * @param dictItemId 字典项ID
     * @return 字典项详情
     */
    @Operation(summary = "获取字典项详情")
    @GetMapping("/get/{dictItemId}")
    public R<SysDictItemResponse> findById(@PathVariable Long dictItemId) {
        return R.ok().build(sysDictItemService.findById(dictItemId));
    }

    /**
     * 分页查询字典项
     *
     * @param query 字典项查询参数
     * @return 分页字典项
     */
    @Operation(summary = "分页查询字典项")
    @GetMapping("/page")
    public R<PageResponse<SysDictItemResponse>> page(SysDictItemQuery query) {
        return R.ok().build(sysDictItemService.findPageList(query));
    }

}
