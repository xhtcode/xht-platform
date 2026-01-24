package com.xht.modules.admin.notice.controller;

import com.xht.framework.core.domain.R;
import com.xht.modules.admin.notice.domain.form.SysNoticeTypeForm;
import com.xht.modules.admin.notice.domain.query.SysNoticeTypeQuery;
import com.xht.modules.admin.notice.domain.response.SysNoticeTypeResponse;
import com.xht.modules.admin.notice.service.ISysNoticeTypeService;
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
    @PostMapping("/create")
    public R<Void> create(@RequestBody SysNoticeTypeForm form) {
        sysNoticeTypeService.create(form);
        return R.ok();
    }

    /**
     * 根据ID删除通知类型
     *
     * @param id 通知类型ID
     */
    @PostMapping("/remove/{id}")
    public R<Void> removeById(@PathVariable Long id) {
        sysNoticeTypeService.removeById(id);
        return R.ok();
    }

    /**
     * 根据ID更新通知类型
     *
     * @param form 通知类型更新请求参数
     */
    @PostMapping("/update")
    public R<Void> updateById(@RequestBody SysNoticeTypeForm form) {
        sysNoticeTypeService.updateById(form);
        return R.ok();
    }

    /**
     * 根据ID查询通知类型
     *
     * @param id 通知类型ID
     * @return 通知类型信息
     */
    @GetMapping("/get/{id}")
    public R<SysNoticeTypeResponse> findById(@PathVariable Long id) {
        return R.ok(sysNoticeTypeService.findById(id));
    }

    /***
     * 查询所有通知类型
     * @param query 通知类型查询请求参数
     * @return 通知类型列表
     */
    @GetMapping("/list")
    public R<List<SysNoticeTypeResponse>> list(SysNoticeTypeQuery query) {
        return R.ok(sysNoticeTypeService.list(query));
    }

}
