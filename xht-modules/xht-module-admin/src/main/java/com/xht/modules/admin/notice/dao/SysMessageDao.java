package com.xht.modules.admin.notice.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.notice.domain.query.SysMessageQuery;
import com.xht.modules.admin.notice.entity.SysMessageEntity;

import java.time.LocalDateTime;

/**
 * 描述 ： 系统管理-站内信主表 Dao
 *
 * @author xht
 **/
public interface SysMessageDao extends MapperRepository<SysMessageEntity> {

    /**
     * 撤回站内信（全部）
     *
     * @param messageId 站内信ID
     * @param cancelTime 撤销时间
     */
    void updateCancelByMessageId(Long messageId, LocalDateTime cancelTime);

    /**
     * 管理员分页查询站内信
     *
     * @param page       分页参数
     * @param query      站内信查询参数
     * @return            站内信分页列表
     */
    Page<SysMessageEntity> findPageList(Page<SysMessageEntity> page, SysMessageQuery query);

}
