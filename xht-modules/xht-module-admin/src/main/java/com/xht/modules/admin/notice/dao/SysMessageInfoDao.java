package com.xht.modules.admin.notice.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.notice.domain.query.SysMessageInfoQuery;
import com.xht.modules.admin.notice.domain.vo.MessageInfoVo;
import com.xht.modules.admin.notice.entity.SysMessageInfoEntity;
import com.xht.modules.admin.notice.enums.MessageStarEnums;
import com.xht.modules.admin.notice.enums.MessageTopEnums;

import java.time.LocalDateTime;

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
     * 收藏站内信（收件人侧）
     *
     * @param messageId        站内信ID
     * @param userId           用户ID
     * @param messageStarEnums 站内信收藏枚举
     */
    void updateStartById(Long messageId, Long userId, MessageStarEnums messageStarEnums);

    /**
     * 置顶站内信（收件人侧）
     *
     * @param messageId       站内信ID
     * @param userId           用户ID
     * @param messageTopEnums 站内信置顶枚举
     */
    void updateTopById(Long messageId, Long userId, MessageTopEnums messageTopEnums);

    /**
     * 删除站内信（收件人侧）
     *
     * @param messageId 站内信ID
     * @param userId    用户ID
     */
    void updateRemoveById(Long messageId, Long userId);

    /**
     * 撤回站内信（全部）
     *
     * @param messageId 站内信ID
     * @param cancelTime 撤销时间
     */
    void updateCancelByMessageId(Long messageId, LocalDateTime cancelTime);

    /**
     * 撤回站内信 （对用户单一撤回）
     *
     * @param messageInfoId 站内信详情ID
     */
    void updateCancelById(Long messageInfoId);

    /**
     * 管理员分页查看站内信发送详情
     *
     * @param page  分页参数
     * @param query 查询参数
     * @return 站内信发送详情
     */
    Page<SysMessageInfoEntity> findAdminPageSend(Page<SysMessageInfoEntity> page, SysMessageInfoQuery query);

    /**
     * 分页查询我接收的站内信
     *
     * @param page  分页参数
     * @param query 查询参数
     * @return  站内信分页
     */
    Page<MessageInfoVo> findMyMessagePageList(Page<SysMessageInfoEntity> page, SysMessageInfoQuery query);

    /**
     * 查询站内信详情
     *
     * @param messageId 站内信ID
     * @param userId    用户ID
     * @return 站内信详情信息
     */
    MessageInfoVo findInfoByMessageId(Long messageId, Long userId);

}
