package com.xht.system.listener;

import com.xht.system.event.SysUserDeptPostUpdateEvent;
import com.xht.system.modules.dept.dao.SysDeptPostDao;
import com.xht.system.modules.dept.domain.entity.SysDeptPostEntity;
import com.xht.system.modules.authority.dao.SysUserDeptPostDao;
import com.xht.system.modules.authority.domain.entity.SysUserDeptPostEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 用户部门更新监听器
 *
 * @author xht
 **/
@Async
@Slf4j
@Component
@RequiredArgsConstructor
public class SysUserDeptPostUpdateListener implements ApplicationListener<SysUserDeptPostUpdateEvent> {

    private final SysUserDeptPostDao sysUserDeptManager;

    private final SysDeptPostDao sysDeptPostManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onApplicationEvent(SysUserDeptPostUpdateEvent event) {
        Long userId = event.getUserId();
        Long oldDeptId = event.getOldDeptId();
        Long oldPostId = event.getOldPostId();
        Long newDeptId = event.getNewDeptId();
        Long newPostId = event.getNewPostId();
        log.info("用户部门更新监听器开始执行 userId:`{}` oldDeptId:`{}` oldPostId:`{}` newDeptId:`{}` newPostId:`{}`", userId, oldDeptId, oldPostId, newDeptId, newPostId);
        //判断信息是否修改
        if (Objects.equals(oldDeptId, newDeptId) && Objects.equals(oldPostId, newPostId)) {
            log.info("用户部门信息未修改，不执行监听器");
            return;
        }
        Boolean oldResult;
        // 删除旧的部门信息  没有之前的部门信息，不执行删除操作
        sysUserDeptManager.deleteBy(oldDeptId, userId, oldPostId);
            //更改旧的岗位已分配的人员人数
        SysDeptPostEntity oldDeptPostEntity = sysDeptPostManager.forUpdateById(oldPostId);
        // 保存新的部门信息
        SysUserDeptPostEntity userDeptEntity = new SysUserDeptPostEntity();
        userDeptEntity.setUserId(userId);
        userDeptEntity.setDeptId(newDeptId);
        userDeptEntity.setPostId(newPostId);
        sysUserDeptManager.save(userDeptEntity);
        //更改新的岗位已分配的人员人数
        SysDeptPostEntity newDeptPostEntity = sysDeptPostManager.forUpdateById(newPostId);
    }
}
