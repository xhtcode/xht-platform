package com.xht.modules.admin.notice.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.notice.entity.SysNoticeEntity;
import com.xht.modules.admin.notice.enums.NoticeStatusEnums;
import com.xht.modules.admin.notice.enums.NoticeTopEnums;

import java.util.List;

/**
 * 描述 ： 系统管理-通知详情 Dao
 *
 * @author xht
 **/
public interface SysNoticeDao extends MapperRepository<SysNoticeEntity> {

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
     * 根据通知id 获取定时发布的数据
     *
     * @param noticeId 通知id
     * @return 定时发布数据
     */
    List<Long> listByReleaseTime(Long noticeId);

}