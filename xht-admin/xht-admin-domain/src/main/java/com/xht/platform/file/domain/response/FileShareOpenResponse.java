package com.xht.platform.file.domain.response;

import com.xht.platform.file.enums.SharePermissionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述： 打开分享响应
 *
 * @author xht
 **/
@Data
@Schema(description = "打开分享响应")
public class FileShareOpenResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "分享票据")
    private String shareTicket;

    @Schema(description = "分享ID")
    private Long shareId;

    @Schema(description = "分享权限")
    private SharePermissionEnum permission;

    @Schema(description = "是否需要提取码")
    private Boolean requireCode;

    @Schema(description = "票据过期时间")
    private LocalDateTime expireAt;

}
