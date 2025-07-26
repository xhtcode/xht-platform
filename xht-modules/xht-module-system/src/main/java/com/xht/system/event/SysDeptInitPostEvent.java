package com.xht.system.event;

import com.xht.framework.core.exception.utils.ThrowUtils;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * 系统部门事件
 *
 * @author xht
 **/
@Getter
public class SysDeptInitPostEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private final Long deptId;

    /**
     * 部门领导ID
     */
    private final Long leaderUserId;

    /**
     * 构造函数就传入deptId和leaderUserId
     *
     * @param deptId       部门ID
     * @param leaderUserId 部门领导ID
     */
    public SysDeptInitPostEvent(Long deptId, Long leaderUserId) {
        super(deptId);
        ThrowUtils.notNull(deptId, "deptId must not be null");
        this.deptId = deptId;
        this.leaderUserId = leaderUserId;
    }
}
