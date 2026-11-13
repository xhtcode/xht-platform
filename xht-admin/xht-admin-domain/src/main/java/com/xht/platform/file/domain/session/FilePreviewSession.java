package com.xht.platform.file.domain.session;

import com.xht.platform.file.enums.FileAccessSubjectTypeEnum;
import com.xht.platform.file.enums.PreviewTypeEnum;
import com.xht.platform.file.enums.SharePermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述： 文件预览会话
 * <p>Redis 存储，绑定访问主体、文件与预览模式</p>
 *
 * @author xht
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilePreviewSession implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 预览ID
     */
    private String previewId;

    /**
     * 主体类型
     */
    private FileAccessSubjectTypeEnum subjectType;

    /**
     * 主体标识：用户ID/匿名会话ID/分享ID/接收人ID
     */
    private String subjectId;

    /**
     * 登录用户ID
     */
    private Long userId;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 是否管理员
     */
    private boolean admin;

    /**
     * 分享权限
     */
    private SharePermissionEnum sharePermission;

    /**
     * 文件ID
     */
    private Long fileId;

    /**
     * 预览模式
     */
    private PreviewTypeEnum previewMode;

    /**
     * 分享ID
     */
    private Long shareId;

    /**
     * 接收人记录ID
     */
    private Long receiverId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 绝对过期时间
     */
    private LocalDateTime absoluteExpireAt;

    /**
     * 滑动 TTL（秒）
     */
    private Long ttlSeconds;

    /**
     * 是否分享主体
     *
     * @return true：分享
     */
    public boolean isShare() {
        return FileAccessSubjectTypeEnum.SHARE_LINK == subjectType
                || FileAccessSubjectTypeEnum.SHARE_USER == subjectType;
    }

}
