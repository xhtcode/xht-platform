package com.xht.system.listener;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.system.dept.domain.entity.SysDeptEntity;
import com.xht.system.dept.domain.entity.SysDeptPostEntity;
import com.xht.system.dept.manager.SysDeptManager;
import com.xht.system.dept.manager.SysDeptPostManager;
import com.xht.system.event.SysDeptInitPostEvent;
import com.xht.system.user.domain.entity.SysUserDeptEntity;
import com.xht.system.user.manager.SysUserDeptManager;
import com.xht.system.user.manager.SysUserManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.xht.framework.core.enums.SystemFlagEnums.NO;
import static com.xht.framework.core.enums.SystemFlagEnums.YES;

/**
 * 岗位及员工分配事件监听器
 *
 * @author xht
 **/
@Async
@Slf4j
@Component
@AllArgsConstructor
public class SysDeptInitPostListener implements ApplicationListener<SysDeptInitPostEvent> {

    private final SysDeptPostManager sysDeptPostManager;

    private final SysUserDeptManager sysUserDeptManager;

    private final SysDeptManager sysDeptManager;

    private final SysUserManager sysUserManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onApplicationEvent(@NonNull SysDeptInitPostEvent event) {
        log.info("岗位及员工分配事件监听器开始处理 部门id：`{}` 负责人id：`{}`", event.getDeptId(), event.getLeaderUserId());
        Long deptId = event.getDeptId();
        List<SysDeptPostEntity> deptPost = new ArrayList<>();
        SysDeptPostEntity sysDeptPostEntity = new SysDeptPostEntity(deptId, "Supervisor", "主管", 0, "主要负责人", 1, YES);
        sysDeptPostEntity.setPostHave(1);
        deptPost.add(sysDeptPostEntity);
        deptPost.add(new SysDeptPostEntity(deptId, "Employee", "员工", 1, "部门员工", 99, NO));
        sysDeptPostManager.saveBatch(deptPost);
        Long initPostId = sysDeptPostEntity.getId();
        LambdaUpdateWrapper<SysDeptEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysDeptEntity::getLeaderPostId, initPostId)
                .eq(SysDeptEntity::getId, deptId);
        sysDeptManager.update(updateWrapper);
        //如果这里就设置了主管的用户id那么直接添加部门主管映射表
        Long leaderUserId = event.getLeaderUserId();
        if (Objects.nonNull(leaderUserId)) {
            SysUserDeptEntity userDeptEntity = new SysUserDeptEntity();
            userDeptEntity.setUserId(leaderUserId);
            userDeptEntity.setDeptId(deptId);
            userDeptEntity.setPostId(initPostId);
            sysUserDeptManager.save(userDeptEntity);
            sysUserManager.updateDept(leaderUserId, deptId);
        }
        log.info("岗位及员工分配事件监听器结束处理");
    }
}
