package com.xht.platform.file.domain.session;

import com.xht.platform.file.enums.FileAccessSubjectTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

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
     * 主体标识：用户ID/匿名会话ID
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
     * 文件ID
     */
    private Long fileId;


}
