package com.xht.modules.admin.notice.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.modules.admin.notice.domain.form.SysNoticeForm;
import com.xht.modules.admin.notice.domain.query.SysNoticeQuery;
import com.xht.modules.admin.notice.domain.response.SysNoticeResponse;
import com.xht.modules.admin.notice.enums.NoticeStatusEnums;
import com.xht.modules.admin.notice.enums.NoticeTopEnums;

import java.util.List;

/**
 * 系统管理-通知详情 的数据库操作Service
 *
 * @author admin
 */
public interface ISysNoticeService {

    /**
     * 创建通知详情
     *
     * @param form 通知详情表单请求参数
     */
    void create(SysNoticeForm form);

    /**
     * 根据ID删除通知详情
     *
     * @param id 通知详情ID
     */
    void removeById(Long id);

    /**
     * 根据ID更新通知详情
     *
     * @param form 通知详情更新请求参数
     */
    void updateById(SysNoticeForm form);

    /**
     * 根据通知id 修改状态
     *
     * @param noticeId     通知id
     * @param noticeStatus 通知状态
     */
    void updateStatusById(Long noticeId, NoticeStatusEnums noticeStatus);

    /**
     * 根据通知id 置顶
     *
     * @param noticeId 通知id
     * @param isTop    是否置顶
     */
    void updateIsTopById(Long noticeId, NoticeTopEnums isTop);

    /**
     * 根据通知id 修改已读人数
     *
     * @param noticeId 通知id
     */
    void updateReadNumById(Long noticeId);

    /**
     * 根据通知id 修改点击次数
     *
     * @param noticeId 通知id
     */
    void updateClickNumById(Long noticeId);

    /**
     * 根据ID查询通知详情
     *
     * @param id 通知详情ID
     * @return 通知详情信息
     */
    SysNoticeResponse findById(Long id);

    /**
     * 根据通知id 获取定时发布的数据
     *
     * @param noticeId 通知id
     * @return 定时发布数据
     */
    List<Long> listByReleaseTime(Long noticeId);

    /**
     * 分页查询通知详情
     *
     * @param query 通知详情查询请求参数
     * @return 通知详情分页信息
     */
    PageResponse<SysNoticeResponse> findPageList(SysNoticeQuery query);

}
