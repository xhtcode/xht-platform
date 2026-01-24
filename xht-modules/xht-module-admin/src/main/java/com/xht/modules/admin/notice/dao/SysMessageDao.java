package com.xht.modules.admin.notice.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.notice.domain.query.SysMessageQuery;
import com.xht.modules.admin.notice.entity.SysMessageEntity;

/**
 * 描述 ： 系统管理-站内信主表 Dao
 *
 * @author xht
 **/
public interface SysMessageDao extends MapperRepository<SysMessageEntity> {

    /**
     * 管理员分页查询站内信
     *
     * @param page       分页参数
     * @param query      站内信查询参数
     * @param sendUserId 发送人ID
     */
    Page<SysMessageEntity> findPageList(Page<SysMessageEntity> page, SysMessageQuery query, Long sendUserId);

}
