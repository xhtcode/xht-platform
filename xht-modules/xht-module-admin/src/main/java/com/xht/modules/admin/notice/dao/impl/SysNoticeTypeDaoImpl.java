package com.xht.modules.admin.notice.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.notice.dao.SysNoticeTypeDao;
import com.xht.modules.admin.notice.dao.mapper.SysNoticeTypeMapper;
import com.xht.modules.admin.notice.domain.form.SysNoticeTypeForm;
import com.xht.modules.admin.notice.domain.query.SysNoticeTypeQuery;
import com.xht.modules.admin.notice.entity.SysNoticeTypeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述 ： 系统管理-通知类型 Dao
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysNoticeTypeDaoImpl extends MapperRepositoryImpl<SysNoticeTypeMapper, SysNoticeTypeEntity> implements SysNoticeTypeDao {

    /**
     * 更新通知类型
     *
     * @param form 通知类型
     */
    @Override
    public void updateFormRequest(SysNoticeTypeForm form) {
        LambdaUpdateWrapper<SysNoticeTypeEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysNoticeTypeEntity::getNoticeTypeName, form.getNoticeTypeName());
        updateWrapper.set(SysNoticeTypeEntity::getNoticeTypeSort, form.getNoticeTypeSort());
        updateWrapper.eq(SysNoticeTypeEntity::getId, form.getId());
        update(updateWrapper);
    }

    /**
     * 查询全部通知类型
     *
     * @param query 通知类型查询请求参数
     * @return 通知类型分页信息
     */
    @Override
    public List<SysNoticeTypeEntity> findList(SysNoticeTypeQuery query) {
        LambdaQueryWrapper<SysNoticeTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(condition(query.getNoticeTypeName()), SysNoticeTypeEntity::getNoticeTypeName, query.getNoticeTypeName());
        queryWrapper.orderByDesc(SysNoticeTypeEntity::getNoticeTypeSort);
        return list(queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysNoticeTypeEntity, ?> getFieldId() {
        return SysNoticeTypeEntity::getId;
    }

}