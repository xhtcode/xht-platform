package com.xht.modules.admin.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.modules.admin.notice.enums.NoticePermTypeEnums;
import lombok.Data;

/**
 * 系统管理-通知权限
 *
 * @author xht
 */
@Data
@TableName(value = "sys_notice_permission")
public class SysNoticePermissionEntity extends BasicEntity {

    /**
     * 权限ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 关联通知ID（关联sys_notice.id）
     */
    @TableField(value = "notice_id")
    private Long noticeId;

    /**
     * 权限类型(0:角色权限;1:用户权限;2:部门权限)
     */
    @TableField(value = "perm_type")
    private NoticePermTypeEnums permType;

    /**
     * 权限值（单个角色ID/用户ID/部门ID，不再存多个）
     */
    @TableField(value = "perm_value")
    private String permValue;

}