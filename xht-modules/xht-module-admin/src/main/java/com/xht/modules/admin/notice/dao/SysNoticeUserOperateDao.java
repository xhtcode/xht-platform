package com.xht.modules.admin.notice.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.notice.entity.SysNoticeUserOperateEntity;
import com.xht.modules.admin.notice.enums.NoticeOperateTypeEnums;

/**
 * 描述 ： 系统管理-用户操作记录(防重复统计) Dao
 *
 * @author xht
 **/
public interface SysNoticeUserOperateDao extends MapperRepository<SysNoticeUserOperateEntity> {

    /**
     * 创建通知用户操作记录
     *
     * @param noticeId    通知ID，标识具体的通知内容
     * @param userId      用户ID，标识执行操作的用户
     * @param operateType 操作类型枚举，表示用户对通知执行的具体操作类型
     */
    void createNoticeUserOperate(Long noticeId, Long userId, NoticeOperateTypeEnums operateType);

    /**
     * 判断通知用户操作记录是否存在
     *
     * @param noticeId    通知ID，标识具体的通知内容
     * @param userId      用户ID，标识执行操作的用户
     * @param operateType 操作类型枚举，表示用户对通知执行的具体操作类型
     * @return boolean 返回true表示该用户对该通知的指定操作记录已存在，返回false表示不存在
     */
    boolean existsNoticeUserOperate(Long noticeId, Long userId, NoticeOperateTypeEnums operateType);

}