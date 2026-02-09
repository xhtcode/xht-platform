package com.xht.modules.admin.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.modules.admin.notice.enums.NoticeTypeStatusEnums;
import lombok.Data;

/**
 * 系统管理-通知类型
 *
 * @author xht
 */
@Data
@TableName(value = "sys_notice_type")
public class SysNoticeTypeEntity extends BasicEntity {

    /**
     * 通知类型
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 类型名称
     */
    @TableField(value = "notice_type_name")
    private String noticeTypeName;

    /**
     * 通知类型状态(0:未启用1:启用)
     */
    @TableField(value = "notice_type_status")
    private NoticeTypeStatusEnums noticeTypeStatus;

    /**
     * 通知排序
     */
    @TableField(value = "notice_type_sort")
    private Integer noticeTypeSort;

}