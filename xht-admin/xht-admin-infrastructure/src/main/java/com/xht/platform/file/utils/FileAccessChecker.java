package com.xht.platform.file.utils;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.utils.AssertUtils;
import com.xht.platform.file.entity.SysFileEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述： 文件访问权限校验器
 * <p>
 * 下载/预览等文件内容访问的越权防护：仅超级管理员或文件上传人本人可访问，
 * 否则抛出 {@link BusinessErrorCode#FILE_ACCESS_DENIED}
 *
 * @author xht
 **/
@Slf4j
@Component
public class FileAccessChecker {

    /**
     * 校验当前用户是否有权访问指定文件
     *
     * @param entity 文件实体
     */
    public void check(SysFileEntity entity) {
        AssertUtils.notNull(entity, BusinessErrorCode.FILE_NOT_EXIST);
        // 超级管理员放行
        if (SecurityUtils.isAdmin()) {
            return;
        }
        String username = SecurityUtils.getUserName();
        // 上传人本人放行
        if (Objects.equals(username, entity.getFileUploadUser())) {
            return;
        }
        log.warn("文件访问越权拦截，fileStorageKey：{}，currentUser：{}，uploadUser：{}",
                entity.getFileStorageKey(), username, entity.getFileUploadUser());
        AssertUtils.fail(BusinessErrorCode.FILE_ACCESS_DENIED);
    }

}
