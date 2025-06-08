package com.xht.system.event;

import com.xht.framework.core.exception.utils.ThrowUtils;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 系统用户部门更新事件
 *
 * @author xht
 **/
@Getter
public class SysUserDeptUpdateEvent extends ApplicationEvent {

    /**
     * 用户id
     */
    private final Long userId;

    /**
     * 旧的部门id
     */
    private final Long oldDeptId;

    /**
     * 旧的岗位id
     */
    private final Long oldPostId;

    /**
     * 新的部门id
     */
    private final Long newDeptId;

    /**
     * 新的岗位id
     */
    private final Long newPostId;


    public SysUserDeptUpdateEvent(Long userId, Long oldDeptId, Long oldPostId, Long newDeptId, Long newPostId) {
        super(userId);
        ThrowUtils.notNull(userId, "用户id 不能为空");
        ThrowUtils.notNull(newDeptId, "新的部门id 不能为空");
        ThrowUtils.notNull(newPostId, "新的岗位id 不能为空");
        this.userId = userId;
        this.oldDeptId = oldDeptId;
        this.oldPostId = oldPostId;
        this.newDeptId = newDeptId;
        this.newPostId = newPostId;
    }
}
