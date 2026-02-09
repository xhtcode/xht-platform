package com.xht.modules.admin.notice.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.framework.mybatis.utils.SortTool;
import com.xht.modules.admin.notice.dao.SysNoticeTypeDao;
import com.xht.modules.admin.notice.dao.mapper.SysNoticeTypeMapper;
import com.xht.modules.admin.notice.domain.form.SysNoticeTypeForm;
import com.xht.modules.admin.notice.domain.query.SysNoticeTypeQuery;
import com.xht.modules.admin.notice.entity.SysNoticeTypeEntity;
import com.xht.modules.admin.notice.enums.NoticeTypeStatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
        QueryWrapper<SysNoticeTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(condition(query.getNoticeTypeName()), "notice_type_name", query.getNoticeTypeName());
        queryWrapper.eq(condition(query.getNoticeTypeStatus()), "notice_type_status", query.getNoticeTypeStatus());
        SortTool.sort(queryWrapper, query);
        return list(queryWrapper);
    }

    /**
     * 获取所有通知类型
     *
     * @return 获取所有通知类型
     */
    @Override
    public List<SysNoticeTypeEntity> findEnableList() {
        LambdaQueryWrapper<SysNoticeTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysNoticeTypeEntity::getId, SysNoticeTypeEntity::getNoticeTypeName);
        queryWrapper.eq(SysNoticeTypeEntity::getNoticeTypeStatus, NoticeTypeStatusEnums.ENABLE);
        queryWrapper.orderByDesc(SysNoticeTypeEntity::getNoticeTypeSort);
        return list(queryWrapper);
    }

    /**
     * 根据通知ID获取通知类型名称
     *
     * @param typeId 通知类型ID
     * @return 通知类型
     */
    @Override
    public String findTypeName(Long typeId) {
        LambdaQueryWrapper<SysNoticeTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysNoticeTypeEntity::getNoticeTypeName);
        queryWrapper.eq(SysNoticeTypeEntity::getId, typeId);
        return Optional.ofNullable(getOne(queryWrapper)).map(SysNoticeTypeEntity::getNoticeTypeName).orElse(null);
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