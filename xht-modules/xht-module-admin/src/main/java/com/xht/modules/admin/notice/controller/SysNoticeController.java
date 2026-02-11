package com.xht.modules.admin.notice.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.validation.Groups;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.modules.admin.notice.domain.form.SysNoticeForm;
import com.xht.modules.admin.notice.domain.query.SysNoticeQuery;
import com.xht.modules.admin.notice.domain.response.SysNoticeResponse;
import com.xht.modules.admin.notice.domain.vo.NoticeVo;
import com.xht.modules.admin.notice.enums.NoticeTopEnums;
import com.xht.modules.admin.notice.service.ISysNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统管理-通知详情
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "系统管理-通知详情", description = "系统管理-通知详情相关的API")
@RestController
@RequestMapping("/sys/notice")
@RequiredArgsConstructor
public class SysNoticeController {

    private final ISysNoticeService sysNoticeService;

    /**
     * 创建系统管理-通知详情
     *
     * @param form 系统管理-通知详情表单请求参数
     * @return 统一响应结果
     */
    @CheckMenu("sys:notice:create")
    @Operation(summary = "创建系统管理-通知详情")
    @PostMapping("/create")
    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody SysNoticeForm form) {
        sysNoticeService.create(form);
        return R.ok().build();
    }

    /**
     * 根据主键`id`删除系统管理-通知详情
     *
     * @param id 系统管理-字典表主键
     * @return 统一响应结果
     */
    @CheckMenu("sys:notice:remove")
    @Operation(summary = "根据主键`id`删除系统管理-通知详情")
    @PostMapping("/remove/{id}")
    public R<Void> remove(@PathVariable Long id) {
        sysNoticeService.removeById(id);
        return R.ok().build();
    }

    /**
     * 根据主键`id`更新系统管理-通知详情
     *
     * @param form 系统管理-通知详情表单请求参数
     * @return 统一响应结果
     */
    @CheckMenu("sys:notice:update")
    @Operation(summary = "根据主键`id`更新系统管理-通知详情")
    @PostMapping("/update")
    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody SysNoticeForm form) {
        sysNoticeService.updateById(form);
        return R.ok().build();
    }

    /**
     * 根据通知id 发布
     *
     * @param noticeId 通知id
     */
    @Operation(summary = "根据通知id 发布")
    @PostMapping("/publish/{noticeId}")
    public R<Void> publishNoticeId(@PathVariable Long noticeId) {
        sysNoticeService.publishNoticeId(noticeId);
        return R.ok().build();
    }

    /**
     * 根据通知id 下架
     *
     * @param noticeId 通知id
     */
    @Operation(summary = "根据通知id 下架")
    @PostMapping("/underShelve/{noticeId}")
    public R<Void> underShelveNoticeId(@PathVariable Long noticeId) {
        sysNoticeService.underShelveNoticeId(noticeId);
        return R.ok().build();
    }

    /**
     * 根据通知id 置顶
     *
     * @param noticeId 通知id
     * @param isTop    是否置顶
     */
    @Operation(summary = "根据通知id 置顶")
    @PostMapping("/top/{noticeId}/{isTop}")
    public R<Void> updateIsTopById(@PathVariable Long noticeId, @PathVariable NoticeTopEnums isTop) {
        sysNoticeService.updateIsTopById(noticeId, isTop);
        return R.ok().build();
    }


    /**
     * 根据主键`id`查询系统管理-通知详情
     *
     * @param id 系统管理-通知详情主键
     * @return 系统管理-通知详情信息
     */
    @Operation(summary = "根据主键`id`查询系统管理-通知详情")
    @IgnoreAuth(aop = false)
    @GetMapping("/get/{id}")
    public R<NoticeVo> findById(@PathVariable("id") Long id) {
        return R.ok().build(sysNoticeService.findById(id));
    }

    /**
     * 分页查询系统管理-通知详情
     *
     * @param query 系统管理-通知详情查询请求参数
     * @return 系统管理-通知详情分页信息
     */
    @Operation(summary = "分页查询系统管理-通知详情")
    @GetMapping("/page")
    public R<PageResponse<SysNoticeResponse>> findPageList(SysNoticeQuery query) {
        return R.ok().build(sysNoticeService.findPageList(query));
    }

}
