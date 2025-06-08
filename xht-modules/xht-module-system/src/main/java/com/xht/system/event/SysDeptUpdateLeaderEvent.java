package com.xht.system.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * 部门更新事件
 *
 * @author xht
 **/
@Getter
public class SysDeptUpdateLeaderEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Long deptId;

    private final Long oldLeaderUserId;

    private final Long newLeaderUserId;

    private final Long leaderPostId;

    public SysDeptUpdateLeaderEvent(Long deptId, Long oldLeaderUserId, Long newLeaderUserId, Long leaderPostId) {
        super(deptId);
        this.deptId = deptId;
        this.oldLeaderUserId = oldLeaderUserId;
        this.newLeaderUserId = newLeaderUserId;
        this.leaderPostId = leaderPostId;
    }
}
