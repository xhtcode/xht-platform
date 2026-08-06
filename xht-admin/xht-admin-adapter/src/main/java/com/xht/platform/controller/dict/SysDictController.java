package com.xht.platform.controller.dict;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.framework.validation.Groups;
import  com.xht.platform.dict.domain.form.SysDictForm;
import  com.xht.platform.dict.domain.query.SysDictQuery;
import  com.xht.platform.dict.domain.response.SysDictResponse;
import  com.xht.platform.dict.service.ISysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @BLog(value = "字典管理", description = "创建字典类型")
    @CheckMenu("sys:dict:create")
    @Operation(summary = "创建字典类型")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysDictForm form) {
        sysDictService.create(form);
        return R.ok().build();
    }

    /**
     * 删除字典类型
     *
     * @param dictId 字典类型ID集合
     * @return true成功、false失败
     */
    @BLog(value = "字典管理", description = "删除字典类型")
    @CheckMenu("sys:dict:remove")
    @Operation(summary = "删除字典类型")
    @PostMapping("/remove/{dictId}")
    public R<Void> remove(@PathVariable Long dictId) {
        sysDictService.removeById(dictId);
        return R.ok().build();
    }

    /**
     * 修改字典类型
     *
     * @param dictId 字典类型ID
     * @param form 字典类型信息
     * @return true成功、false失败
     */
    @BLog(value = "字典管理", description = "修改字典类型")
    @CheckMenu("sys:dict:updateById")
    @Operation(summary = "修改字典类型")
    @PostMapping("/update/{dictId}")
    public R<Void> updateById(@PathVariable Long dictId, @Validated(value = {Groups.Update.class}) @RequestBody SysDictForm form) {
        sysDictService.updateById(dictId, form);
        return R.ok().build();
    }

    /**
     * 获取字典类型详情
     *
     * @param dictId 字典类型ID
     * @return 字典类型详情
     */
    @Operation(summary = "获取字典类型详情")
    @GetMapping("/get/{dictId}")
    public R<SysDictResponse> findById(@PathVariable Long dictId) {
        return R.ok().build(sysDictService.findById(dictId));
    }

    /**
     * 分页查询字典类型
     *
     * @param query 系统字典查询参数
     * @return 分页结果
     */
    @Operation(summary = "分页查询字典类型")
    @GetMapping("/page")
    public R<PageResponse<SysDictResponse>> findPageList(SysDictQuery query) {
        return R.ok().build(sysDictService.findPageList(query));
    }


}
