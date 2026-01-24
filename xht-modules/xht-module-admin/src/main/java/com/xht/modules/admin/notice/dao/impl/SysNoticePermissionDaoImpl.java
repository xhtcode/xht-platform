package com.xht.modules.admin.notice.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.notice.dao.SysNoticePermissionDao;
import com.xht.modules.admin.notice.dao.mapper.SysNoticePermissionMapper;
import com.xht.modules.admin.notice.entity.SysNoticePermissionEntity;
import com.xht.modules.admin.notice.enums.NoticePermTypeEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述 ： 系统管理-通知权限 Dao
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysNoticePermissionDaoImpl extends MapperRepositoryImpl<SysNoticePermissionMapper, SysNoticePermissionEntity> implements SysNoticePermissionDao {

    /**
     * 根据通知id 删除所有权限信息
     *
     * @param id 通知id
     */
    @Override
    public void removeByNoticeId(Long id) {
        LambdaUpdateWrapper<SysNoticePermissionEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysNoticePermissionEntity::getNoticeId, id);
        remove(updateWrapper);
    }

    /**
     * 根据通知id 获取所有权限信息
     *
     * @param noticeId 通知id
     * @return 权限信息
     */
    @Override
    public Set<String> getByNoticeId(Long noticeId) {
        LambdaQueryWrapper<SysNoticePermissionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysNoticePermissionEntity::getNoticeId, noticeId);
        return format(list(queryWrapper));
    }

    /**
     * 根据通知id 获取所有权限信息
     *
     * @param noticeId 通知id
     * @param permType 权限类型
     * @return 权限信息
     */
    @Override
    public Set<String> getByNoticeIdAndPermType(Long noticeId, NoticePermTypeEnums permType) {
        LambdaQueryWrapper<SysNoticePermissionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysNoticePermissionEntity::getNoticeId, noticeId);
        queryWrapper.eq(SysNoticePermissionEntity::getPermType, permType);
        return format(list(queryWrapper));
    }

    /**
     * 判断用户是否有权限
     *
     * @param noticeId  通知id
     * @param userId    用户id
     * @param deptId    部门id
     * @param roleCodes 角色编码
     * @return true:有权限 false:无权限
     */
    @Override
    public boolean hashPermission(Long noticeId, Long userId, Long deptId, Set<String> roleCodes) {
        LambdaQueryWrapper<SysNoticePermissionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysNoticePermissionEntity::getNoticeId, noticeId);
        // @formatter:off
        queryWrapper.and(wrapper -> {
            wrapper.or(dataScopeWrapper -> {
                dataScopeWrapper
                        .eq(SysNoticePermissionEntity::getPermType, NoticePermTypeEnums.ROLE_PERM)
                        .in(SysNoticePermissionEntity::getPermValue, roleCodes);
            });
            wrapper.or(dataScopeWrapper -> {
                dataScopeWrapper
                        .eq(SysNoticePermissionEntity::getPermType, NoticePermTypeEnums.USER_PERM)
                        .eq(SysNoticePermissionEntity::getPermValue, userId);
            });
            wrapper.or(dataScopeWrapper -> {
                dataScopeWrapper
                        .eq(SysNoticePermissionEntity::getPermType, NoticePermTypeEnums.DEPT_PERM)
                        .eq(SysNoticePermissionEntity::getPermValue, deptId);
            });
        });
        // @formatter:on
        return exists(queryWrapper);
    }

    /**
     * 格式化权限信息
     *
     * @param entityList 权限信息
     * @return 格式化后的权限信息
     */
    private Set<String> format(List<SysNoticePermissionEntity> entityList) {
        // @formatter:off
        return Optional.ofNullable(entityList)
                .orElse(Collections.emptyList())
                .stream()
                .map(SysNoticePermissionEntity::getPermValue)
                .collect(Collectors.toSet());
        // @formatter:on
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysNoticePermissionEntity, ?> getFieldId() {
        return SysNoticePermissionEntity::getId;
    }

}