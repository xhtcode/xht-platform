package com.xht.platform.file.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.Entity;
import com.xht.platform.file.enums.ShareModeEnum;
import com.xht.platform.file.enums.SharePermissionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 描述： 文件分享实体
 * <p>LINK 链接分享 / USER 指定用户分享；提取码可选；状态控制撤销与失效</p>
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_file_share")
public class SysFileShareEntity extends Entity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分享定位串
     */
    @TableField(value = "share_token")
    private String shareToken;

    /**
     * 分享模式：LINK/USER
     */
    @TableField(value = "share_mode")
    private ShareModeEnum shareMode;

    /**
     * 文件ID
     */
    @TableField(value = "file_id")
    private Long fileId;

    /**
     * 创建人ID
     */
    @TableField(value = "creator_id")
    private Long creatorId;

    /**
     * 创建人名称
     */
    @TableField(value = "creator_name")
    private String creatorName;

    /**
     * 分享权限
     */
    @TableField(value = "permission")
    private SharePermissionEnum permission;

    /**
     * 是否需要提取码：1 是
     */
    @TableField(value = "require_code")
    private Integer requireCode;

    /**
     * 提取码版本，重置时递增
     */
    @TableField(value = "code_version")
    private Integer codeVersion;

    /**
     * 提取码哈希（BCrypt），requireCode=0 时为空
     */
    @TableField(value = "access_code_hash")
    private String accessCodeHash;

    /**
     * 过期时间
     */
    @TableField(value = "expire_time")
    private LocalDateTime expireTime;

    /**
     * 访问次数上限：0=不限，仅 LINK 生效
     */
    @TableField(value = "max_access_count")
    private Integer maxAccessCount;

    /**
     * 访问次数
     */
    @TableField(value = "access_count")
    private Integer accessCount;

    /**
     * 接收范围类型：USER/DEPT/ROLE/MIXED
     */
    @TableField(value = "target_type")
    private String targetType;

    /**
     * 接收范围摘要
     */
    @TableField(value = "target_name")
    private String targetName;

    /**
     * 接收人数
     */
    @TableField(value = "receiver_count")
    private Integer receiverCount;

    /**
     * 是否已发通知：1 是
     */
    @TableField(value = "notice_sent")
    private Integer noticeSent;

    /**
     * 状态：1正常 0撤销 2失效
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 最后访问时间
     */
    @TableField(value = "last_access_time")
    private LocalDateTime lastAccessTime;

    /**
     * 最后访问IP
     */
    @TableField(value = "last_access_ip")
    private String lastAccessIp;

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

    /**
     * 更新人
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
