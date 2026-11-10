package com.xht.platform.file.permission;

import com.xht.platform.file.domain.access.FileAccessDecision;
import com.xht.platform.file.domain.access.FileAccessSubject;
import com.xht.platform.file.entity.SysFileEntity;

/**
 * 描述： 文件业务权限提供者
 * <p>业务模块按文件用途注册实现，返回查看/下载两个独立权限位；
 * 多个实现同时命中时取交集，未命中任何实现时私有文件默认拒绝</p>
 *
 * @author xht
 **/
public interface FilePermissionProvider {

    /**
     * 是否处理该文件的授权判定
     *
     * @param file 文件实体
     * @return true：参与判定
     */
    boolean supports(SysFileEntity file);

    /**
     * 返回授权决策
     *
     * @param file    文件实体
     * @param subject 访问主体
     * @return 授权决策
     */
    FileAccessDecision decide(SysFileEntity file, FileAccessSubject subject);

}
