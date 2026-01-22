package com.xht.modules.admin.notice.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.notice.domain.query.SysMessageInfoQuery;
import com.xht.modules.admin.notice.entity.SysMessageInfoEntity;

/**
 * 系统管理-站内信收件人明细表 Dao
 *
 * @author xht
 **/
public interface SysMessageInfoDao extends MapperRepository<SysMessageInfoEntity> {

    /**
     * 已读站内信（收件人侧）
     *
     * @param messageId 站内信ID
     * @param userId    用户ID
     */
    void updateReadById(Long messageId, Long userId);

    /**
     * 删除站内信（收件人侧）
     *
     * @param messageId 站内信ID
     * @param userId    用户ID
     */
    void updateRemoveById(Long messageId, Long userId);

    /**
     * 撤回站内信（发件人侧）
     *
     * @param messageId 站内信ID
     */
    void updateCancelById(Long messageId);

    /**
     * 管理员分页查看站内信发送详情
     *
     * @param page  分页参数
     * @param query 查询参数
     * @return 站内信发送详情
     */
    Page<SysMessageInfoEntity> findAdminPageSend(Page<SysMessageInfoEntity> page, SysMessageInfoQuery query);

    /**
     * 分页查询我的站内信
     *
     * @param page  分页参数
     * @param query 查询参数
     */
    Page<SysMessageInfoEntity> findMyMessagePageList(Page<SysMessageInfoEntity> page, SysMessageInfoQuery query);

}
