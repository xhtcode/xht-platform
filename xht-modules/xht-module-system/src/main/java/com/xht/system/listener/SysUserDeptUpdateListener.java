package com.xht.system.listener;

import com.xht.system.event.SysUserDeptUpdateEvent;
import com.xht.system.modules.dept.dao.SysDeptPostDao;
import com.xht.system.modules.dept.domain.entity.SysDeptPostEntity;
import com.xht.system.modules.user.dao.SysUserDeptDao;
import com.xht.system.modules.user.domain.entity.SysUserDeptEntity;
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
public class SysUserDeptUpdateListener implements ApplicationListener<SysUserDeptUpdateEvent> {

    private final SysUserDeptDao sysUserDeptManager;

    private final SysDeptPostDao sysDeptPostManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onApplicationEvent(SysUserDeptUpdateEvent event) {
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
        oldResult = sysDeptPostManager.updatePostHave(oldPostId, oldDeptPostEntity.getPostHave() - 1);
        // 保存新的部门信息
        SysUserDeptEntity userDeptEntity = new SysUserDeptEntity();
        userDeptEntity.setUserId(userId);
        userDeptEntity.setDeptId(newDeptId);
        userDeptEntity.setPostId(newPostId);
        sysUserDeptManager.save(userDeptEntity);
        //更改新的岗位已分配的人员人数
        SysDeptPostEntity newDeptPostEntity = sysDeptPostManager.forUpdateById(newPostId);
        Boolean newResult = sysDeptPostManager.updatePostHave(newPostId, newDeptPostEntity.getPostHave() - 1);
        log.info("用户部门更新监听器执行结束 oldResult:`{}` newResult:`{}`", oldResult, newResult);
    }
}
