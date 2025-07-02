package com.xht.system.dict.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.system.dict.domain.request.SysDictItemFormRequest;
import com.xht.system.dict.domain.request.SysDictItemQueryRequest;
import com.xht.system.dict.domain.response.SysDictItemResponse;
import com.xht.system.dict.domain.vo.SysDictVo;
import com.xht.system.dict.service.ISysDictItemService;
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
     * @param formRequest 字典项创建参数
     * @return true成功、false失败
     */
    @Operation(summary = "创建字典项")
    @PostMapping("/add")
    public R<Boolean> create(@Validated(value = {Groups.Create.class}) @RequestBody SysDictItemFormRequest formRequest) {
        return R.ok(sysDictItemService.create(formRequest));
    }

    /**
     * 删除字典项
     *
     * @param ids 字典项ID
     * @return true成功、false失败
     */
    @Operation(summary = "删除字典项")
    @PostMapping("/delete")
    public R<Boolean> deleteById(@RequestBody List<Long> ids) {
        return R.ok(sysDictItemService.deleteById(ids));
    }

    /**
     * 修改字典项
     *
     * @param formRequest 字典项修改参数
     * @return true成功、false失败
     */
    @Operation(summary = "修改字典项")
    @PostMapping("/update")
     public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody SysDictItemFormRequest formRequest) {
        return R.ok(sysDictItemService.updateById(formRequest));
    }

    /**
     * 获取字典项详情
     *
     * @param id 字典项ID
     * @return 字典项详情
     */
    @Operation(summary = "获取字典项详情")
    @GetMapping("/get/{id}")
    public R<SysDictItemResponse> getById(@PathVariable Long id) {
        return R.ok(sysDictItemService.getById(id));
    }

    /**
     * 分页查询字典项
     *
     * @param queryRequest 字典项查询参数
     * @return 分页字典项
     */
    @Operation(summary = "分页查询字典项")
    @GetMapping("/page")
    public R<PageResponse<SysDictItemResponse>> page(SysDictItemQueryRequest queryRequest) {
        return R.ok(sysDictItemService.page(queryRequest));
    }

    /**
     * 根据字典编码查询
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    @Operation(summary = "根据字典编码查询")
    @GetMapping("/code/{dictCode}")
    public R<SysDictVo> getByDictCode(@PathVariable String dictCode) {
        return R.ok(sysDictItemService.getByDictCode(dictCode));
    }

}
