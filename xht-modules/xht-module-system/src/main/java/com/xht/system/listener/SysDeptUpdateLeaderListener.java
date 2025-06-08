package com.xht.system.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.system.event.SysDeptUpdateLeaderEvent;
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

/**
 * 系统部门主管修改事件监听器
 *
 * @author xht
 **/
@Async
@Slf4j
@Component
@AllArgsConstructor
public class SysDeptUpdateLeaderListener implements ApplicationListener<SysDeptUpdateLeaderEvent> {

    private final SysUserDeptManager sysUserDeptManager;

    private final SysUserManager sysUserManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onApplicationEvent(@NonNull SysDeptUpdateLeaderEvent event) {
        Long deptId = event.getDeptId();
        Long oldLeaderUserId = event.getOldLeaderUserId();
        Long newLeaderUserId = event.getNewLeaderUserId();
        Long leaderPostId = event.getLeaderPostId();
        log.info("系统部门主管修改事件监听器开始处理 部门id：`{}` 原负责人id：`{}` 新负责人id：`{}` 主管岗位id：`{}`", deptId, oldLeaderUserId, newLeaderUserId, leaderPostId);
        //删除原先的部门主管岗位关系
        LambdaQueryWrapper<SysUserDeptEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SysUserDeptEntity::getDeptId, deptId)
                .eq(SysUserDeptEntity::getUserId, oldLeaderUserId)
                .eq(SysUserDeptEntity::getPostId, leaderPostId);
        sysUserDeptManager.remove(queryWrapper);
        //新增新的部门主管岗位关系
        SysUserDeptEntity userDeptEntity = sysUserDeptManager.getById(oldLeaderUserId);
        userDeptEntity.setUserId(newLeaderUserId);
        userDeptEntity.setDeptId(deptId);
        userDeptEntity.setPostId(leaderPostId);
        sysUserDeptManager.save(userDeptEntity);
        //修改原先主管用户表的部门id
        sysUserManager.updateDept(oldLeaderUserId, deptId);
        //修改新的主管用户表的部门id
        sysUserManager.updateDept(newLeaderUserId, deptId);
        log.info("系统部门主管修改事件监听器结束处理");
    }
}
