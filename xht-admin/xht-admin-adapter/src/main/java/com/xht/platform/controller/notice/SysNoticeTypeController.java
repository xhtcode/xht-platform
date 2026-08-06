package com.xht.platform.controller.notice;

import com.xht.framework.common.domain.LabelValue;
import com.xht.framework.common.domain.R;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.security.annotation.IgnoreAuth;
import  com.xht.platform.notice.domain.form.SysNoticeTypeForm;
import  com.xht.platform.notice.domain.query.SysNoticeTypeQuery;
import  com.xht.platform.notice.domain.response.SysNoticeTypeResponse;
import  com.xht.platform.notice.service.ISysNoticeTypeService;
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
@Tag(name = "系统管理-通知类型管理")
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
    @BLog(value = "通知类型", description = "创建通知类型")
    @Operation(summary = "创建通知类型")
    @PostMapping("/create")
    public R<Void> create(@RequestBody SysNoticeTypeForm form) {
        sysNoticeTypeService.create(form);
        return R.ok().build();
    }

    /**
     * 根据ID删除通知类型
     *
     * @param noticeTypeId 通知类型ID
     */
    @BLog(value = "通知类型", description = "根据ID删除通知类型")
    @Operation(summary = "根据ID删除通知类型")
    @PostMapping("/remove/{noticeTypeId}")
    public R<Void> removeById(@PathVariable Long noticeTypeId) {
        sysNoticeTypeService.removeById(noticeTypeId);
        return R.ok().build();
    }

    /**
     * 根据ID更新通知类型
     *
     * @param noticeTypeId 通知类型ID
     * @param form 通知类型更新请求参数
     */
    @BLog(value = "通知类型", description = "根据ID更新通知类型")
    @Operation(summary = "根据ID更新通知类型")
    @PostMapping("/update/{noticeTypeId}")
    public R<Void> updateById(@PathVariable Long noticeTypeId, @RequestBody SysNoticeTypeForm form) {
        sysNoticeTypeService.updateById(noticeTypeId, form);
        return R.ok().build();
    }

    /**
     * 根据ID查询通知类型
     *
     * @param noticeTypeId 通知类型ID
     * @return 通知类型信息
     */
    @Operation(summary = "根据ID查询通知类型")
    @GetMapping("/get/{noticeTypeId}")
    public R<SysNoticeTypeResponse> findById(@PathVariable Long noticeTypeId) {
        return R.ok().build(sysNoticeTypeService.findById(noticeTypeId));
    }

    /**
     * 查询所有通知类型
     * @param query 通知类型查询请求参数
     * @return 通知类型列表
     */
    @GetMapping("/list")
    @Operation(summary = "查询所有通知类型")
    public R<List<SysNoticeTypeResponse>> list(SysNoticeTypeQuery query) {
        return R.ok().build(sysNoticeTypeService.list(query));
    }

    /**
     * 查询所有通知类型
     * @return 通知类型列表
     */
    @IgnoreAuth(aop = false)
    @GetMapping("/enable/all")
    @Operation(summary = "查询所有通知类型")
    public R<List<LabelValue<Long, String>>> findEnableList() {
        return R.ok().build(sysNoticeTypeService.findEnableList());
    }

}
