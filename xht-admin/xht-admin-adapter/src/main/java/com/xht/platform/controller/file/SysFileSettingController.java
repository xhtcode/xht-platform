package com.xht.platform.controller.file;

import com.xht.framework.common.domain.R;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.IsAdmin;
import com.xht.platform.file.domain.form.SysFileSettingUpdateForm;
import com.xht.platform.file.domain.response.SysFileSettingResponse;
import com.xht.platform.file.service.ISysFileSettingAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述： 文件全局配置管理接口
 *
 * @author xht
 **/
@Tag(name = "文件全局配置")
@RestController
@RequestMapping("/sys/files/settings")
@RequiredArgsConstructor
public class SysFileSettingController {

    private final ISysFileSettingAdminService sysFileSettingAdminService;

    /**
     * 配置列表
     *
     * @param group 分组
     * @return 配置列表
     */
    @IsAdmin
    @Operation(summary = "配置列表")
    @GetMapping
    public R<List<SysFileSettingResponse>> list(@RequestParam(required = false) String group) {
        return R.ok().build(sysFileSettingAdminService.list(group));
    }

    /**
     * 批量更新配置
     *
     * @param form 更新请求
     * @return 更新数量
     */
    @IsAdmin
    @BLog(value = "文件配置", description = "更新文件全局配置")
    @Operation(summary = "批量更新配置")
    @PutMapping
    public R<Integer> update(@RequestBody SysFileSettingUpdateForm form) {
        return R.ok().build(sysFileSettingAdminService.update(form));
    }

    /**
     * 恢复默认值
     *
     * @param keys 配置键
     * @return 恢复数量
     */
    @IsAdmin
    @BLog(value = "文件配置", description = "恢复文件配置默认值")
    @Operation(summary = "恢复默认值")
    @PostMapping("/reset")
    public R<Integer> reset(@RequestBody List<String> keys) {
        return R.ok().build(sysFileSettingAdminService.reset(keys));
    }

}
