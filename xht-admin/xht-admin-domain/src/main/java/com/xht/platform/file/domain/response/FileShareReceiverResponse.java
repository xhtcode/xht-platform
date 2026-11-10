package com.xht.platform.file.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述： 分享接收人
 *
 * @author xht
 **/
@Data
@Schema(description = "分享接收人")
public class FileShareReceiverResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "接收人记录ID")
    private Long receiverId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "已读状态：0未读 1已读")
    private Integer readStatus;

    @Schema(description = "读取时间")
    private LocalDateTime readTime;

    @Schema(description = "首次查看时间")
    private LocalDateTime firstViewTime;

    @Schema(description = "下载次数")
    private Integer downloadCount;

}
