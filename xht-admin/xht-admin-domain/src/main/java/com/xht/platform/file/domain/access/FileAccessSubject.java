package com.xht.platform.file.domain.access;

import com.xht.platform.file.enums.FileAccessSubjectTypeEnum;
import com.xht.platform.file.enums.SharePermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 文件访问主体
 * <p>描述“谁在访问”，用于权限决策；分享访问携带分享权限</p>
 *
 * @author xht
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileAccessSubject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主体类型
     */
    private FileAccessSubjectTypeEnum type;

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
     * 分享ID
     */
    private Long shareId;

    /**
     * 接收人记录ID
     */
    private Long receiverId;

    /**
     * 分享权限
     */
    private SharePermissionEnum sharePermission;

    /**
     * 构造登录用户主体
     *
     * @param userId   用户ID
     * @param userName 用户名
     * @param admin    是否管理员
     * @return 主体
     */
    public static FileAccessSubject user(Long userId, String userName, boolean admin) {
        return FileAccessSubject.builder()
                .type(FileAccessSubjectTypeEnum.USER)
                .userId(userId)
                .userName(userName)
                .admin(admin)
                .build();
    }

    /**
     * 构造匿名公开访问主体
     *
     * @return 主体
     */
    public static FileAccessSubject anonymous() {
        return FileAccessSubject.builder()
                .type(FileAccessSubjectTypeEnum.ANON)
                .build();
    }

    /**
     * 构造链接分享主体
     *
     * @param shareId        分享ID
     * @param sharePermission 分享权限
     * @return 主体
     */
    public static FileAccessSubject shareLink(Long shareId, SharePermissionEnum sharePermission) {
        return FileAccessSubject.builder()
                .type(FileAccessSubjectTypeEnum.SHARE_LINK)
                .shareId(shareId)
                .sharePermission(sharePermission)
                .build();
    }

    /**
     * 构造指定用户分享主体
     *
     * @param shareId         分享ID
     * @param receiverId      接收人记录ID
     * @param sharePermission 分享权限
     * @return 主体
     */
    public static FileAccessSubject shareUser(Long shareId, Long receiverId, SharePermissionEnum sharePermission) {
        return FileAccessSubject.builder()
                .type(FileAccessSubjectTypeEnum.SHARE_USER)
                .shareId(shareId)
                .receiverId(receiverId)
                .sharePermission(sharePermission)
                .build();
    }

    /**
     * 是否登录用户主体
     *
     * @return true：登录用户
     */
    public boolean isUser() {
        return FileAccessSubjectTypeEnum.USER == type;
    }

    /**
     * 是否分享主体
     *
     * @return true：分享访问
     */
    public boolean isShare() {
        return FileAccessSubjectTypeEnum.SHARE_LINK == type || FileAccessSubjectTypeEnum.SHARE_USER == type;
    }

}
