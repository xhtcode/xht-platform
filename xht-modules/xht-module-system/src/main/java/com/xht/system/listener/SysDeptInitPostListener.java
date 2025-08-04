package com.xht.system.listener;

import com.xht.system.event.SysDeptInitPostEvent;
import com.xht.system.modules.dept.dao.SysDeptDao;
import com.xht.system.modules.dept.dao.SysDeptPostDao;
import com.xht.system.modules.dept.domain.entity.SysDeptPostEntity;
import com.xht.system.modules.user.dao.SysUserDao;
import com.xht.system.modules.authority.dao.SysUserDeptPostDao;
import com.xht.system.modules.authority.domain.entity.SysUserDeptPostEntity;
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

    private final SysDeptPostDao sysDeptPostDao;

    private final SysUserDeptPostDao sysUserDeptPostDao;

    private final SysDeptDao sysDeptDao;

    private final SysUserDao sysUserDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onApplicationEvent(@NonNull SysDeptInitPostEvent event) {
        log.info("岗位及员工分配事件监听器开始处理 部门id：`{}` 负责人id：`{}`", event.getDeptId(), event.getLeaderUserId());
        Long deptId = event.getDeptId();
        List<SysDeptPostEntity> deptPost = new ArrayList<>();
        SysDeptPostEntity sysDeptPostEntity = new SysDeptPostEntity(deptId, "Supervisor", "主管", 0, "主要负责人", YES);
        deptPost.add(sysDeptPostEntity);
        deptPost.add(new SysDeptPostEntity(deptId, "Employee", "员工", 1, "部门员工", NO));
        sysDeptPostDao.saveAll(deptPost);
        Long initPostId = sysDeptPostEntity.getId();
        sysDeptDao.updateLeaderPostId(deptId, initPostId);
        //如果这里就设置了主管的用户id那么直接添加部门主管映射表
        Long leaderUserId = event.getLeaderUserId();
        if (Objects.nonNull(leaderUserId)) {
            SysUserDeptPostEntity userDeptEntity = new SysUserDeptPostEntity();
            userDeptEntity.setUserId(leaderUserId);
            userDeptEntity.setDeptId(deptId);
            userDeptEntity.setPostId(initPostId);
            sysUserDeptPostDao.save(userDeptEntity);
            sysUserDao.updateDept(leaderUserId, deptId);
        }
        log.info("岗位及员工分配事件监听器结束处理");
    }
}
