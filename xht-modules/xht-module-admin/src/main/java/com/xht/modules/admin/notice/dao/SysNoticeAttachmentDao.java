package com.xht.modules.admin.notice.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.notice.entity.SysNoticeAttachmentEntity;

import java.util.List;

/**
 * 描述 ： 系统管理-通知附件 Dao
 *
 * @author xht
 **/
public interface SysNoticeAttachmentDao extends MapperRepository<SysNoticeAttachmentEntity> {

    /**
     * 根据通知id 删除所有附件信息
     *
     * @param noticeId 通知id
     */
    void removeByNoticeId(Long noticeId);

    /**
     * 根据通知id 查询所有附件信息
     *
     * @param noticeId 通知id
     * @return 附件信息
     */
    List<SysNoticeAttachmentEntity> findListByNoticeId(Long noticeId);
}
