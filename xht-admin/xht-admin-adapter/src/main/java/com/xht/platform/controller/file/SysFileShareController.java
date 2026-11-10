package com.xht.platform.controller.file;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.platform.file.domain.form.FileShareCreateForm;
import com.xht.platform.file.domain.form.FileShareOpenForm;
import com.xht.platform.file.domain.query.SysFileShareQuery;
import com.xht.platform.file.domain.query.SysFileShareReceivedQuery;
import com.xht.platform.file.domain.response.FileShareMetaResponse;
import com.xht.platform.file.domain.response.FileShareOpenResponse;
import com.xht.platform.file.domain.response.FileShareReceiverResponse;
import com.xht.platform.file.domain.response.FileShareReceivedResponse;
import com.xht.platform.file.domain.response.FileShareResponse;
import com.xht.platform.file.service.ISysFileShareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述： 文件分享接口
 * <p>支持链接分享、指定用户分享、提取码、分享中心</p>
 *
 * @author xht
 **/
@Tag(name = "文件分享")
@RestController
@RequestMapping("/sys/files")
@RequiredArgsConstructor
public class SysFileShareController {

    private final ISysFileShareService fileShareService;

    /**
     * 创建分享
     *
     * @param form 创建请求
     * @return 分享信息
     */
    @BLog(value = "文件分享", description = "创建文件分享")
    @Operation(summary = "创建文件分享")
    @PostMapping("/preview/share")
    public R<FileShareResponse> create(@RequestBody FileShareCreateForm form) {
        return R.ok().build(fileShareService.create(form));
    }

    /**
     * 链接分享元信息
     *
     * @param token 分享定位串
     * @return 元信息
     */
    @IgnoreAuth(aop = false)
    @Operation(summary = "链接分享元信息", parameters = {
            @Parameter(name = "token", description = "分享定位串", required = true)
    })
    @GetMapping("/share/link/{token}/meta")
    public R<FileShareMetaResponse> metaByToken(@PathVariable String token) {
        return R.ok().build(fileShareService.metaByToken(token));
    }

    /**
     * 打开链接分享
     *
     * @param token   分享定位串
     * @param form    打开请求
     * @param request 请求
     * @return 分享票据
     */
    @IgnoreAuth(aop = false)
    @Operation(summary = "打开链接分享")
    @PostMapping("/share/link/{token}/open")
    public R<FileShareOpenResponse> openByToken(@PathVariable String token,
                                                @RequestBody(required = false) FileShareOpenForm form,
                                                HttpServletRequest request) {
        return R.ok().build(fileShareService.openByToken(token, form, request));
    }

    /**
     * 指定用户分享元信息
     *
     * @param shareId 分享ID
     * @return 元信息
     */
    @Operation(summary = "指定用户分享元信息")
    @GetMapping("/share/user/{shareId}/meta")
    public R<FileShareMetaResponse> metaByShareId(@PathVariable Long shareId) {
        return R.ok().build(fileShareService.metaByShareId(shareId));
    }

    /**
     * 打开指定用户分享
     *
     * @param shareId 分享ID
     * @param form    打开请求
     * @return 分享票据
     */
    @Operation(summary = "打开指定用户分享")
    @PostMapping("/share/user/{shareId}/open")
    public R<FileShareOpenResponse> openByShareId(@PathVariable Long shareId,
                                                  @RequestBody(required = false) FileShareOpenForm form) {
        return R.ok().build(fileShareService.openByShareId(shareId, form));
    }

    /**
     * 我创建的分享
     *
     * @param query 查询条件
     * @return 分页
     */
    @Operation(summary = "我创建的分享")
    @PostMapping("/share/page")
    public R<PageResponse<FileShareResponse>> pageCreated(@RequestBody SysFileShareQuery query) {
        return R.ok().build(fileShareService.pageCreated(query));
    }

    /**
     * 分享给我的
     *
     * @param query 查询条件
     * @return 分页
     */
    @Operation(summary = "分享给我的")
    @PostMapping("/share/received/page")
    public R<PageResponse<FileShareReceivedResponse>> pageReceived(@RequestBody SysFileShareReceivedQuery query) {
        return R.ok().build(fileShareService.pageReceived(query));
    }

    /**
     * 未读数量
     *
     * @return 未读数
     */
    @Operation(summary = "分享未读数量")
    @GetMapping("/share/received/unread-count")
    public R<Long> unreadCount() {
        return R.ok().build(fileShareService.unreadCount());
    }

    /**
     * 分享接收人
     *
     * @param shareId 分享ID
     * @return 接收人列表
     */
    @Operation(summary = "分享接收人")
    @GetMapping("/share/{shareId}/receivers")
    public R<List<FileShareReceiverResponse>> receivers(@PathVariable Long shareId) {
        return R.ok().build(fileShareService.receivers(shareId));
    }

    /**
     * 撤销分享
     *
     * @param shareId 分享ID
     * @return 操作结果
     */
    @BLog(value = "文件分享", description = "撤销文件分享")
    @Operation(summary = "撤销分享")
    @DeleteMapping("/share/{shareId}")
    public R<Void> revoke(@PathVariable Long shareId) {
        fileShareService.revoke(shareId);
        return R.ok().build();
    }

    /**
     * 重置提取码
     *
     * @param shareId    分享ID
     * @param accessCode 新提取码，空则随机
     * @return 新提取码
     */
    @BLog(value = "文件分享", description = "重置分享提取码")
    @Operation(summary = "重置提取码")
    @PostMapping("/share/{shareId}/reset-code")
    public R<String> resetCode(@PathVariable Long shareId,
                               @RequestParam(required = false) String accessCode) {
        return R.ok().build(fileShareService.resetCode(shareId, accessCode));
    }

    /**
     * 标记已读
     *
     * @param shareId 分享ID
     * @return 操作结果
     */
    @Operation(summary = "标记分享已读")
    @PostMapping("/share/{shareId}/read")
    public R<Void> markRead(@PathVariable Long shareId) {
        fileShareService.markRead(shareId);
        return R.ok().build();
    }

    /**
     * 从我的列表移除
     *
     * @param shareId 分享ID
     * @return 操作结果
     */
    @Operation(summary = "移除分享")
    @PostMapping("/share/{shareId}/hide")
    public R<Void> hide(@PathVariable Long shareId) {
        fileShareService.hide(shareId);
        return R.ok().build();
    }

}
