package com.xht.system.dict.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.web.validation.Groups;
import com.xht.system.dict.domain.request.SysDictFormRequest;
import com.xht.system.dict.domain.request.SysDictQueryRequest;
import com.xht.system.dict.domain.response.SysDictResponse;
import com.xht.system.dict.service.ISysDictService;
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
     * @param formRequest 字典类型信息
     * @return true成功、false失败
     */
    @Operation(summary = "创建字典类型")
    @PostMapping("/add")
    public R<Boolean> create(@Validated(value = {Groups.Create.class}) @RequestBody SysDictFormRequest formRequest) {
        return R.ok(sysDictService.create(formRequest));
    }

    /**
     * 删除字典类型
     *
     * @param ids 字典类型ID集合
     * @return true成功、false失败
     */
    @Operation(summary = "删除字典类型")
    @PostMapping("/delete")
    public R<Boolean> deleteById(@RequestBody List<Long> ids) {
        return R.ok(sysDictService.deleteById(ids));
    }

    /**
     * 修改字典类型
     *
     * @param formRequest 字典类型信息
     * @return true成功、false失败
     */
    @Operation(summary = "修改字典类型")
    @PostMapping("/update")
     public R<Boolean> updateById(@Validated(value = {Groups.Update.class}) @RequestBody SysDictFormRequest formRequest) {
        return R.ok(sysDictService.updateById(formRequest));
    }

    /**
     * 获取字典类型详情
     *
     * @param id 字典类型ID
     * @return 字典类型详情
     */
    @Operation(summary = "获取字典类型详情")
    @GetMapping("/get/{id}")
    public R<SysDictResponse> getById(@PathVariable Long id) {
        return R.ok(sysDictService.getById(id));
    }

    /**
     * 分页查询字典类型
     *
     * @param queryRequest 系统字典查询参数
     * @return 分页结果
     */
    @Operation(summary = "分页查询字典类型")
    @GetMapping("/page")
    public R<PageResponse<SysDictResponse>> findPage(SysDictQueryRequest queryRequest) {
        return R.ok(sysDictService.findPage(queryRequest));
    }

}
