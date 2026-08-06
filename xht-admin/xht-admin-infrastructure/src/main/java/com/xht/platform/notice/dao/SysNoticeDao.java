package com.xht.platform.notice.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import  com.xht.platform.notice.domain.form.SysNoticeForm;
import  com.xht.platform.notice.domain.query.SysNoticeQuery;
import  com.xht.platform.notice.domain.response.SysNoticeResponse;
import  com.xht.platform.notice.entity.SysNoticeEntity;
import  com.xht.platform.notice.enums.NoticeStatusEnum;
import  com.xht.platform.notice.enums.NoticeTopEnum;

import java.util.List;

/**
 * 描述 ： 系统管理-通知详情 Dao
 *
 * @author xht
 **/
public interface SysNoticeDao extends MapperRepository<SysNoticeEntity> {

    /**
     * 根据主键`id`更新系统管理-通知详情
     *
     * @param noticeId  通知id
     * @param form     系统管理-通知详情表单请求参数
     */
    void updateFormRequest(Long noticeId, SysNoticeForm form);

    /**
     * 根据通知id 修改状态
     *
     * @param noticeId     通知id
     * @param noticeStatus 通知状态
     */
    void updateStatusById(Long noticeId, NoticeStatusEnum noticeStatus);

    /**
     * 根据通知id 置顶
     *
     * @param noticeId 通知id
     * @param isTop    是否置顶
     */
    void updateIsTopById(Long noticeId, NoticeTopEnum isTop);

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
     * 根据通知id 获取定时发布的数据
     *
     * @param noticeId 通知id
     * @return 定时发布数据
     */
    List<Long> listByReleaseTime(Long noticeId);

    /**
     * 分页查询
     *
     * @param page         分页参数
     * @param noticeQuery  查询参数
     * @return 分页数据
     */
    Page<SysNoticeResponse> findPageList(Page<SysNoticeEntity> page, SysNoticeQuery noticeQuery);

}