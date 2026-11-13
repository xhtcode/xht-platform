package com.xht.platform.file.domain.access;

import com.xht.platform.file.enums.FileAccessSubjectTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 文件访问主体
 * <p>描述“谁在访问”，用于权限决策</p>
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
     * 是否登录用户主体
     *
     * @return true：登录用户
     */
    public boolean isUser() {
        return FileAccessSubjectTypeEnum.USER == type;
    }

}
