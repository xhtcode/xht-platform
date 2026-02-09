package com.xht.modules.admin.notice.controller;

import com.xht.framework.core.domain.LabelValue;
import com.xht.framework.core.domain.R;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.modules.admin.notice.domain.form.SysNoticeTypeForm;
import com.xht.modules.admin.notice.domain.query.SysNoticeTypeQuery;
import com.xht.modules.admin.notice.domain.response.SysNoticeTypeResponse;
import com.xht.modules.admin.notice.service.ISysNoticeTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述 ： 系统管理-通知类型 控制器
 *
 * @author xht
 **/
@Tag(name = "通知类型")
@RestController
@RequestMapping("/sys/notice/type")
@RequiredArgsConstructor
public class SysNoticeTypeController {

    private final ISysNoticeTypeService sysNoticeTypeService;

    /**
     * 创建通知类型
     *
     * @param form 通知类型表单请求参数
     */
    @Operation(summary = "创建通知类型")
    @PostMapping("/create")
    public R<Void> create(@RequestBody SysNoticeTypeForm form) {
        sysNoticeTypeService.create(form);
        return R.ok().build();
    }

    /**
     * 根据ID删除通知类型
     *
     * @param id 通知类型ID
     */
    @Operation(summary = "根据ID删除通知类型")
    @PostMapping("/remove/{id}")
    public R<Void> removeById(@PathVariable Long id) {
        sysNoticeTypeService.removeById(id);
        return R.ok().build();
    }

    /**
     * 根据ID更新通知类型
     *
     * @param form 通知类型更新请求参数
     */
    @Operation(summary = "根据ID更新通知类型")
    @PostMapping("/update")
    public R<Void> updateById(@RequestBody SysNoticeTypeForm form) {
        sysNoticeTypeService.updateById(form);
        return R.ok().build();
    }

    /**
     * 根据ID查询通知类型
     *
     * @param id 通知类型ID
     * @return 通知类型信息
     */
    @Operation(summary = "根据ID查询通知类型")
    @GetMapping("/get/{id}")
    public R<SysNoticeTypeResponse> findById(@PathVariable Long id) {
        return R.ok().build(sysNoticeTypeService.findById(id));
    }

    /**
     * 查询所有通知类型
     * @param query 通知类型查询请求参数
     * @return 通知类型列表
     */
    @Operation(summary = "查询所有通知类型")
    @GetMapping("/list")
    public R<List<SysNoticeTypeResponse>> list(SysNoticeTypeQuery query) {
        return R.ok().build(sysNoticeTypeService.list(query));
    }

    /**
     * 查询所有通知类型
     * @return 通知类型列表
     */
    @Operation(summary = "查询所有通知类型")
    @IgnoreAuth(aop = false)
    @GetMapping("/enable/all")
    public R<List<LabelValue<Long, String>>> findEnableList() {
        return R.ok().build(sysNoticeTypeService.findEnableList());
    }

}
