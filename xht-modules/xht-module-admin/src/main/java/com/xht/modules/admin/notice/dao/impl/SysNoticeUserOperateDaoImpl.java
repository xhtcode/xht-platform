package com.xht.modules.admin.notice.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.notice.dao.SysNoticeUserOperateDao;
import com.xht.modules.admin.notice.dao.mapper.SysNoticeUserOperateMapper;
import com.xht.modules.admin.notice.entity.SysNoticeUserOperateEntity;
import com.xht.modules.admin.notice.enums.NoticeOperateTypeEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 描述 ： 系统管理-用户操作记录(防重复统计) Dao
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysNoticeUserOperateDaoImpl extends MapperRepositoryImpl<SysNoticeUserOperateMapper, SysNoticeUserOperateEntity> implements SysNoticeUserOperateDao {

    /**
     * 创建通知用户操作记录
     *
     * @param noticeId    通知ID，标识具体的通知内容
     * @param userId      用户ID，标识执行操作的用户
     * @param operateType 操作类型枚举，表示用户对通知执行的具体操作类型
     */
    @Override
    public void createNoticeUserOperate(Long noticeId, Long userId, NoticeOperateTypeEnums operateType) {
        SysNoticeUserOperateEntity entity = new SysNoticeUserOperateEntity();
        entity.setNoticeId(noticeId);
        entity.setUserId(userId);
        entity.setOperateType(operateType);
        entity.setOperateTime(LocalDateTime.now());
        this.save(entity);
    }

    /**
     * 判断通知用户操作记录是否存在
     *
     * @param noticeId    通知ID，标识具体的通知内容
     * @param userId      用户ID，标识执行操作的用户
     * @param operateType 操作类型枚举，表示用户对通知执行的具体操作类型
     * @return boolean 返回true表示该用户对该通知的指定操作记录已存在，返回false表示不存在
     */
    @Override
    public boolean existsNoticeUserOperate(Long noticeId, Long userId, NoticeOperateTypeEnums operateType) {
        LambdaQueryWrapper<SysNoticeUserOperateEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysNoticeUserOperateEntity::getNoticeId, noticeId);
        queryWrapper.eq(SysNoticeUserOperateEntity::getUserId, userId);
        queryWrapper.eq(SysNoticeUserOperateEntity::getOperateType, operateType);
        return exists(queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysNoticeUserOperateEntity, ?> getFieldId() {
        return SysNoticeUserOperateEntity::getId;
    }

}