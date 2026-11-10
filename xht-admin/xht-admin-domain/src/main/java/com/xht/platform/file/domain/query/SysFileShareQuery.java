package com.xht.platform.file.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import com.xht.platform.file.enums.ShareModeEnum;
import com.xht.platform.file.enums.SharePermissionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 描述： 我创建的分享分页查询参数
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "我创建的分享分页查询参数")
public class SysFileShareQuery extends PageBasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件名关键字
     */
    @Schema(description = "文件名关键字")
    private String fileName;

    /**
     * 分享模式
     */
    @Schema(description = "分享模式")
    private ShareModeEnum shareMode;

    /**
     * 分享权限
     */
    @Schema(description = "分享权限")
    private SharePermissionEnum permission;

    /**
     * 状态：1正常 0撤销 2失效
     */
    @Schema(description = "状态")
    private Integer status;

    /**
     * 创建时间起
     */
    @Schema(description = "创建时间起")
    private LocalDateTime startTime;

    /**
     * 创建时间止
     */
    @Schema(description = "创建时间止")
    private LocalDateTime endTime;

    /**
     * 创建人ID，服务端填充，不对外暴露
     */
    @Schema(hidden = true)
    private Long creatorId;

}
