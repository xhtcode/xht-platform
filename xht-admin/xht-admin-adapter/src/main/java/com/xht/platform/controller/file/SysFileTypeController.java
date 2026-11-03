package com.xht.platform.controller.file;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.validation.Groups;
import com.xht.platform.file.domain.form.SysFileTypeForm;
import com.xht.platform.file.domain.query.SysFileTypeQuery;
import com.xht.platform.file.domain.response.SysFileTypeResponse;
import com.xht.platform.file.service.ISysFileTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述： 文件预览类型配置接口
 * <p>
 * 提供文件预览类型配置（sys_file_type）的新增/删除/修改/详情/分页/列表，以及按文件后缀查询预览类型；
 * 写操作标注 {@link BLog} 记录操作日志
 *
 * @author xht
 **/
@Tag(name = "文件预览类型配置")
@RestController
@RequestMapping("/sys/files/type")
@RequiredArgsConstructor
public class SysFileTypeController {

    private final ISysFileTypeService sysFileTypeService;

    /**
     * 创建文件预览类型配置
     *
     * @param form 文件预览类型配置表单
     * @return 操作结果
     */
    @Operation(summary = "创建文件预览类型配置")
    @PostMapping("/create")
    public R<Void> create(@Validated(Groups.Create.class) @RequestBody SysFileTypeForm form) {
        sysFileTypeService.create(form);
        return R.ok().build();
    }

    /**
     * 根据ID删除文件预览类型配置
     *
     * @param fileTypeId 配置ID
     * @return 操作结果
     */
    @Operation(summary = "根据ID删除文件预览类型配置")
    @PostMapping("/remove/{fileTypeId}")
    public R<Void> removeById(@PathVariable Long fileTypeId) {
        sysFileTypeService.removeById(fileTypeId);
        return R.ok().build();
    }

    /**
     * 根据ID更新文件预览类型配置
     *
     * @param fileTypeId 配置ID
     * @param form       文件预览类型配置表单
     * @return 操作结果
     */
    @Operation(summary = "根据ID更新文件预览类型配置")
    @PostMapping("/update/{fileTypeId}")
    public R<Void> updateById(@PathVariable Long fileTypeId, @Validated(Groups.Update.class) @RequestBody SysFileTypeForm form) {
        sysFileTypeService.updateById(fileTypeId, form);
        return R.ok().build();
    }

    /**
     * 根据ID查询文件预览类型配置
     *
     * @param fileTypeId 配置ID
     * @return 文件预览类型配置
     */
    @Operation(summary = "根据ID查询文件预览类型配置")
    @GetMapping("/get/{fileTypeId}")
    public R<SysFileTypeResponse> findById(@PathVariable Long fileTypeId) {
        return R.ok().build(sysFileTypeService.findById(fileTypeId));
    }

    /**
     * 分页查询文件预览类型配置
     *
     * @param query 分页查询参数
     * @return 分页结果
     */
    @Operation(summary = "分页查询文件预览类型配置")
    @PostMapping("/page")
    public R<PageResponse<SysFileTypeResponse>> findPageList(@RequestBody SysFileTypeQuery query) {
        return R.ok().build(sysFileTypeService.findPageList(query));
    }

}
