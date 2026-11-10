package com.xht.platform.file.domain.session;

import com.xht.platform.file.enums.ShareModeEnum;
import com.xht.platform.file.enums.SharePermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述： 分享访问票据
 *
 * @author xht
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareTicket implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 票据
     */
    private String ticket;

    /**
     * 分享ID
     */
    private Long shareId;

    /**
     * 文件ID
     */
    private Long fileId;

    /**
     * 分享模式
     */
    private ShareModeEnum shareMode;

    /**
     * 分享权限
     */
    private SharePermissionEnum permission;

    /**
     * 接收人记录ID，链接分享为空
     */
    private Long receiverId;

    /**
     * 提取码版本
     */
    private Integer codeVersion;

    /**
     * 票据过期时间
     */
    private LocalDateTime expireAt;

}
