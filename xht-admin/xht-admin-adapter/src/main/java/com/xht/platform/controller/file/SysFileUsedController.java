package com.xht.platform.controller.file;

import com.xht.framework.common.domain.R;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.IsAdmin;
import com.xht.platform.file.domain.form.SysFileUsedForm;
import com.xht.platform.file.domain.response.SysFileUsedResponse;
import com.xht.platform.file.service.ISysFileUsedAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述： 文件用途管理接口
 *
 * @author xht
 **/
@Tag(name = "文件用途管理")
@RestController
@RequestMapping("/sys/files/used")
@RequiredArgsConstructor
public class SysFileUsedController {

    private final ISysFileUsedAdminService sysFileUsedAdminService;

    /**
     * 启用用途列表
     *
     * @return 用途列表
     */
    @Operation(summary = "启用用途列表")
    @GetMapping("/list")
    public R<List<SysFileUsedResponse>> list() {
        return R.ok().build(sysFileUsedAdminService.listEnabled());
    }

    /**
     * 全部用途列表
     *
     * @return 用途列表
     */
    @IsAdmin
    @Operation(summary = "全部用途列表")
    @GetMapping("/list-all")
    public R<List<SysFileUsedResponse>> listAll() {
        return R.ok().build(sysFileUsedAdminService.listAll());
    }

    /**
     * 新增用途
     *
     * @param form 表单
     * @return 操作结果
     */
    @IsAdmin
    @BLog(value = "文件用途", description = "新增文件用途")
    @Operation(summary = "新增用途")
    @PostMapping("/create")
    public R<Void> create(@RequestBody SysFileUsedForm form) {
        sysFileUsedAdminService.create(form);
        return R.ok().build();
    }

    /**
     * 更新用途
     *
     * @param id   用途ID
     * @param form 表单
     * @return 操作结果
     */
    @IsAdmin
    @BLog(value = "文件用途", description = "更新文件用途")
    @Operation(summary = "更新用途")
    @PostMapping("/update/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysFileUsedForm form) {
        sysFileUsedAdminService.update(id, form);
        return R.ok().build();
    }

    /**
     * 停用用途
     *
     * @param id 用途ID
     * @return 操作结果
     */
    @IsAdmin
    @BLog(value = "文件用途", description = "停用文件用途")
    @Operation(summary = "停用用途")
    @PostMapping("/disable/{id}")
    public R<Void> disable(@PathVariable Long id) {
        sysFileUsedAdminService.disable(id);
        return R.ok().build();
    }

    /**
     * 删除用途
     *
     * @param id 用途ID
     * @return 操作结果
     */
    @IsAdmin
    @BLog(value = "文件用途", description = "删除文件用途")
    @Operation(summary = "删除用途")
    @PostMapping("/remove/{id}")
    public R<Void> remove(@PathVariable Long id) {
        sysFileUsedAdminService.remove(id);
        return R.ok().build();
    }

}
