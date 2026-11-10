package com.xht.platform.file.domain.response;

import com.xht.platform.file.enums.ShareModeEnum;
import com.xht.platform.file.enums.SharePermissionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述： 分享元信息
 *
 * @author xht
 **/
@Data
@Schema(description = "分享元信息")
public class FileShareMetaResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "分享ID")
    private Long shareId;

    @Schema(description = "分享模式")
    private ShareModeEnum shareMode;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件大小信息")
    private String fileSizeInfo;

    @Schema(description = "文件后缀")
    private String fileSuffix;

    @Schema(description = "分享人")
    private String creatorName;

    @Schema(description = "分享权限")
    private SharePermissionEnum permission;

    @Schema(description = "是否需要提取码")
    private Boolean requireCode;

    @Schema(description = "过期时间")
    private LocalDateTime expireAt;

    @Schema(description = "状态：1正常 0撤销 2失效")
    private Integer status;

}
