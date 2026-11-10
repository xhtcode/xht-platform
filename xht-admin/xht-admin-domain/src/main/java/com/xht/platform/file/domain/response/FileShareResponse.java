package com.xht.platform.file.domain.response;

import com.xht.platform.file.enums.ShareModeEnum;
import com.xht.platform.file.enums.SharePermissionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述： 文件分享信息
 *
 * @author xht
 **/
@Data
@Schema(description = "文件分享信息")
public class FileShareResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "分享ID")
    private Long shareId;

    @Schema(description = "分享模式")
    private ShareModeEnum shareMode;

    @Schema(description = "分享权限")
    private SharePermissionEnum permission;

    @Schema(description = "是否需要提取码")
    private Boolean requireCode;

    @Schema(description = "分享定位串，仅 LINK 返回")
    private String shareToken;

    @Schema(description = "分享链接，仅 LINK 返回")
    private String shareUrl;

    @Schema(description = "提取码明文，仅创建时返回一次")
    private String accessCode;

    @Schema(description = "过期时间")
    private LocalDateTime expireAt;

    @Schema(description = "访问次数上限")
    private Integer maxAccessCount;

    @Schema(description = "访问次数")
    private Integer accessCount;

    @Schema(description = "接收人数")
    private Integer receiverCount;

    @Schema(description = "状态：1正常 0撤销 2失效")
    private Integer status;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件大小信息")
    private String fileSizeInfo;

    @Schema(description = "文件后缀")
    private String fileSuffix;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
