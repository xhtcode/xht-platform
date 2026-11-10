package com.xht.platform.file.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.platform.file.domain.access.ShareAccessContext;
import com.xht.platform.file.domain.form.FileShareCreateForm;
import com.xht.platform.file.domain.form.FileShareOpenForm;
import com.xht.platform.file.domain.query.SysFileShareQuery;
import com.xht.platform.file.domain.query.SysFileShareReceivedQuery;
import com.xht.platform.file.domain.response.FileShareMetaResponse;
import com.xht.platform.file.domain.response.FileShareOpenResponse;
import com.xht.platform.file.domain.response.FileShareReceiverResponse;
import com.xht.platform.file.domain.response.FileShareReceivedResponse;
import com.xht.platform.file.domain.response.FileShareResponse;
import com.xht.platform.file.domain.session.FilePreviewSession;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 描述： 文件分享服务接口
 *
 * @author xht
 **/
public interface ISysFileShareService {

    /**
     * 创建分享
     *
     * @param form 创建请求
     * @return 分享信息
     */
    FileShareResponse create(FileShareCreateForm form);

    /**
     * 链接分享元信息
     *
     * @param token 分享定位串
     * @return 元信息
     */
    FileShareMetaResponse metaByToken(String token);

    /**
     * 指定用户分享元信息
     *
     * @param shareId 分享ID
     * @return 元信息
     */
    FileShareMetaResponse metaByShareId(Long shareId);

    /**
     * 打开链接分享
     *
     * @param token   分享定位串
     * @param form    打开请求
     * @param request 请求
     * @return 票据
     */
    FileShareOpenResponse openByToken(String token, FileShareOpenForm form, HttpServletRequest request);

    /**
     * 打开指定用户分享
     *
     * @param shareId 分享ID
     * @param form    打开请求
     * @return 票据
     */
    FileShareOpenResponse openByShareId(Long shareId, FileShareOpenForm form);

    /**
     * 解析分享票据
     *
     * @param ticket 票据
     * @return 分享访问上下文
     */
    ShareAccessContext resolveTicket(String ticket);

    /**
     * 校验分享会话仍然有效（撤销、过期、接收人删除后立即失效）
     *
     * @param session 预览会话
     */
    void validateSession(FilePreviewSession session);

    /**
     * 我创建的分享
     *
     * @param query 查询条件
     * @return 分页
     */
    PageResponse<FileShareResponse> pageCreated(SysFileShareQuery query);

    /**
     * 分享给我的
     *
     * @param query 查询条件
     * @return 分页
     */
    PageResponse<FileShareReceivedResponse> pageReceived(SysFileShareReceivedQuery query);

    /**
     * 分享接收人列表
     *
     * @param shareId 分享ID
     * @return 接收人
     */
    List<FileShareReceiverResponse> receivers(Long shareId);

    /**
     * 撤销分享
     *
     * @param shareId 分享ID
     */
    void revoke(Long shareId);

    /**
     * 重置提取码
     *
     * @param shareId 分享ID
     * @param code    自定义提取码，空则随机
     * @return 新提取码
     */
    String resetCode(Long shareId, String code);

    /**
     * 未读数量
     *
     * @return 未读数
     */
    long unreadCount();

    /**
     * 标记已读
     *
     * @param shareId 分享ID
     */
    void markRead(Long shareId);

    /**
     * 从列表移除
     *
     * @param shareId 分享ID
     */
    void hide(Long shareId);

}
