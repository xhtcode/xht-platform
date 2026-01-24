package com.xht.modules.admin.notice.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.notice.entity.SysNoticePermissionEntity;
import com.xht.modules.admin.notice.enums.NoticePermTypeEnums;

import java.util.Set;

/**
 * 描述 ： 系统管理-通知权限 Dao
 *
 * @author xht
 **/
public interface SysNoticePermissionDao extends MapperRepository<SysNoticePermissionEntity> {

    /**
     * 根据通知id 删除所有权限信息
     *
     * @param id 通知id
     */
    void removeByNoticeId(Long id);

    /**
     * 根据通知id 获取所有权限信息
     *
     * @param noticeId 通知id
     * @return 权限信息
     */
    Set<String> getByNoticeId(Long noticeId);

    /**
     * 根据通知id 获取所有权限信息
     *
     * @param noticeId 通知id
     * @param permType 权限类型
     * @return 权限信息
     */
    Set<String> getByNoticeIdAndPermType(Long noticeId, NoticePermTypeEnums permType);

    /**
     * 判断用户是否有权限
     *
     * @param noticeId  通知id
     * @param userId    用户id
     * @param deptId    部门id
     * @param roleCodes 角色编码
     * @return true:有权限 false:无权限
     */
    boolean hashPermission(Long noticeId, Long userId, Long deptId, Set<String> roleCodes);

}