package com.xht.modules.admin.notice.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.modules.admin.notice.domain.query.SysMessageInfoQuery;
import com.xht.modules.admin.notice.domain.query.SysMessageQuery;
import com.xht.modules.admin.notice.domain.response.SysMessageResponse;
import com.xht.modules.admin.notice.domain.vo.MessageInfoVo;
import com.xht.modules.admin.notice.domain.vo.MessagePageVo;
import com.xht.modules.admin.notice.enums.MessageStarEnums;
import com.xht.modules.admin.notice.enums.MessageTopEnums;
import com.xht.modules.admin.notice.service.ISysMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 描述 ： 系统管理-站内信
 *
 * @author xht
 **/
@Tag(name = "站内信")
@RestController
@RequestMapping("/sys/message")
@RequiredArgsConstructor
public class SysMessageController {

    private final ISysMessageService sysMessageService;

    /**
     * 全部已读（收件人侧）
     *
     */
    @Operation(summary = "已读站内信（收件人侧）")
    @PostMapping("/update/read")
    public R<Void> updateReadById() {
        sysMessageService.updateReadAll();
        return R.ok().build();
    }

    /**
     * 已读站内信（收件人侧）
     *
     * @param messageId 站内信ID
     */
    @Operation(summary = "已读站内信（收件人侧）")
    @PostMapping("/update/read/{messageId}")
    public R<Void> updateReadById(@PathVariable Long messageId) {
        sysMessageService.updateReadById(messageId);
        return R.ok().build();
    }

    /**
     * 收藏站内信（收件人侧）
     * @param messageId 站内信ID
     * @param messageStarEnums  站内信收藏枚举
     */
    @Operation(summary = "收藏站内信（收件人侧）")
    @PostMapping("/update/start/{messageId}/{start}")
    public R<Void> updateStartById(@PathVariable Long messageId, @PathVariable("start") MessageStarEnums messageStarEnums) {
        sysMessageService.updateStartById(messageId, messageStarEnums);
        return R.ok().build();
    }

    /**
     * 置顶站内信（收件人侧）
     * @param messageId 站内信ID
     * @param messageTopEnums  站内信置顶枚举
     */
    @Operation(summary = "置顶站内信（收件人侧）")
    @PostMapping("/update/top/{messageId}/{top}")
    public R<Void> updateTopById(@PathVariable Long messageId, @PathVariable("top") MessageTopEnums messageTopEnums) {
        sysMessageService.updateTopById(messageId, messageTopEnums);
        return R.ok().build();
    }

    /**
     * 删除站内信（收件人侧）
     *
     * @param messageId 站内信ID
     */
    @Operation(summary = "删除站内信（收件人侧）")
    @PostMapping("/update/remove/{messageId}")
    public R<Void> updateRemoveById(@PathVariable Long messageId) {
        sysMessageService.updateRemoveById(messageId);
        return R.ok().build();
    }

    /**
     * 撤回站内信（全部）
     *
     * @param messageId 站内信ID
     */
    @Operation(summary = "撤回站内信（全部）")
    @PostMapping("/cancel/all/{messageId}")
    public R<Void> updateCancelAllById(@PathVariable Long messageId) {
        sysMessageService.updateCancelAllById(messageId);
        return R.ok().build();
    }

    /**
     * 撤回站内信 （对用户单一撤回）
     * @param messageInfoId 站内信详情ID
     */
    @Operation(summary = "撤回站内信 （对用户单一撤回）")
    @PostMapping("/cancel/single/{messageInfoId}")
    public R<Void> updateCancelSingleById(@PathVariable Long messageInfoId) {
        sysMessageService.updateCancelSingleByInfoId(messageInfoId);
        return R.ok().build();
    }


    /**
     * 查询站内信详情
     *
     * @param messageId 站内信ID
     */
    @Operation(summary = "查询站内信详情")
    @GetMapping("/get/{messageId}")
    public R<MessageInfoVo> findInfoByMessageId(@PathVariable Long messageId) {
        return R.ok().build(sysMessageService.findInfoByMessageId(messageId));
    }

    /**
     * 管理员分页查询站内信
     *
     * @param query 查询参数
     */
    @Operation(summary = "管理员分页查询站内信")
    @GetMapping("/admin/page")
    public R<PageResponse<SysMessageResponse>> findAdminPage(SysMessageQuery query) {
        return R.ok().build(sysMessageService.findAdminPage(query));
    }

    /**
     * 管理员分页查看站内信发送详情
     *
     * @param query 查询参数
     */
    @Operation(summary = "管理员分页查看站内信发送详情")
    @GetMapping("/admin/send/page")
    public R<MessagePageVo> findAdminPageSend(SysMessageInfoQuery query) {
        return R.ok().build(sysMessageService.findAdminPageSend(query));
    }


    /**
     * 分页查询我接收的站内信
     *
     * @param query 查询参数
     * @return  站内信分页列表
     */
    @Operation(summary = "分页查询我接收的站内信")
    @GetMapping("/my/page")
    public R<PageResponse<MessageInfoVo>> findMyPage(SysMessageInfoQuery query) {
        return R.ok().build(sysMessageService.findMyPage(query));
    }

}
