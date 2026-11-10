package com.xht.platform.file.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 描述： 文件分享接收人实体
 * <p>指定用户分享创建时按快照展开落库，记录已读、首次查看与下载次数</p>
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_file_share_receiver")
public class SysFileShareReceiverEntity extends Entity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分享ID
     */
    @TableField(value = "share_id")
    private Long shareId;

    /**
     * 接收用户ID
     */
    @TableField(value = "receiver_id")
    private Long receiverId;

    /**
     * 接收人名称
     */
    @TableField(value = "receiver_name")
    private String receiverName;

    /**
     * 来源类型：USER/DEPT/ROLE
     */
    @TableField(value = "receiver_source")
    private String receiverSource;

    /**
     * 来源部门/角色ID
     */
    @TableField(value = "source_id")
    private Long sourceId;

    /**
     * 已读状态：0未读 1已读
     */
    @TableField(value = "read_status")
    private Integer readStatus;

    /**
     * 读取时间
     */
    @TableField(value = "read_time")
    private LocalDateTime readTime;

    /**
     * 首次查看时间
     */
    @TableField(value = "first_view_time")
    private LocalDateTime firstViewTime;

    /**
     * 下载次数
     */
    @TableField(value = "download_count")
    private Integer downloadCount;

    /**
     * 是否从列表移除：1 是
     */
    @TableField(value = "hidden")
    private Integer hidden;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
