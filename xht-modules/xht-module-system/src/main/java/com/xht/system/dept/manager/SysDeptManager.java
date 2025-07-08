package com.xht.system.dept.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.framework.core.utils.spring.SpringContextUtil;
import com.xht.framework.mybatis.manager.BasicManager;
import com.xht.system.dept.common.enums.DeptStatusEnums;
import com.xht.system.dept.domain.entity.SysDeptEntity;
import com.xht.system.dept.mapper.SysDeptMapper;
import com.xht.system.dept.mapper.SysDeptPostMapper;
import com.xht.system.event.SysDeptInitPostEvent;
import com.xht.system.user.mapper.SysUserDeptMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 部门管理Manager
 *
 * @author xht
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SysDeptManager extends BasicManager<SysDeptMapper, SysDeptEntity> {

    private final SysDeptPostMapper sysDeptPostMapper;

    private final SysUserDeptMapper sysUserDeptMapper;

    /**
     * 保存部门初始化数据
     *
     * @param entity 部门实体
     * @return true：成功；false：失败
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDeptInitPost(SysDeptEntity entity) {
        boolean save = save(entity);
        if (save) {
            SpringContextUtil.publishEvent(new SysDeptInitPostEvent(entity.getId(), entity.getLeaderUserId()));
        }
        return save;
    }


    /**
     * 更新部门信息
     *
     * @param formRequest     部门更新请求
     * @param oldLeaderUserId 旧的主管用户id
     * @return true：成功；false：失败
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateFormRequest(SysDeptEntity formRequest, Long oldLeaderUserId) {
        LambdaUpdateWrapper<SysDeptEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper.set(SysDeptEntity::getDeptName, formRequest.getDeptName())
                .set(SysDeptEntity::getDeptCode, formRequest.getDeptCode())
                .set(SysDeptEntity::getDeptName, formRequest.getDeptName())
                .set(SysDeptEntity::getParentId, formRequest.getParentId())
                .set(SysDeptEntity::getDeptStatus, formRequest.getDeptStatus())
                .set(SysDeptEntity::getDeptSort, formRequest.getDeptSort())
                .set(SysDeptEntity::getDeptLevel, formRequest.getDeptLevel())
                .set(SysDeptEntity::getAncestors, formRequest.getAncestors())
                .set(SysDeptEntity::getLeaderUserId, formRequest.getLeaderUserId())
                .set(SysDeptEntity::getLeaderPostId, formRequest.getLeaderPostId())
                .set(SysDeptEntity::getLeaderName, formRequest.getLeaderName())
                .set(SysDeptEntity::getPhone, formRequest.getPhone())
                .set(SysDeptEntity::getEmail, formRequest.getEmail())
                .set(SysDeptEntity::getRemark, formRequest.getRemark())
                .eq(SysDeptEntity::getId, formRequest.getId());
        // @formatter:on
        boolean update = update(updateWrapper);
        if (update && !Objects.equals(oldLeaderUserId, formRequest.getLeaderUserId())) {

        }
        return update;
    }

    /**
     * 更新部门状态
     *
     * @param id     部门id
     * @param status 部门状态
     * @return true：成功；false：失败
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(Long id, DeptStatusEnums status) {
        LambdaUpdateWrapper<SysDeptEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(SysDeptEntity::getDeptStatus, status.getValue())
                .eq(SysDeptEntity::getId, id);
        // @formatter:on
        return update(updateWrapper);
    }

    /**
     * 检查部门编码是否唯一
     *
     * @param deptId   部门id
     * @param deptCode 部门编码
     * @return true：唯一；false：不唯一
     */
    public Boolean checkDeptCodeUnique(Long deptId, String deptCode) {
        //@formatter:off
        LambdaQueryWrapper<SysDeptEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(SysDeptEntity::getDeptCode, deptCode)
                .ne(Objects.nonNull(deptId), SysDeptEntity::getId, deptId);
        //@formatter:on
        return dataExists(count(lambdaQueryWrapper));
    }

    /**
     * 获取上级父部门信息
     *
     * @param parentId 父部门id
     * @return 父部门
     */
    public SysDeptEntity getDefaultParentDeptByParentId(Long parentId) {
        if (Objects.equals(parentId, 0L)) {
            SysDeptEntity sysDeptEntity = new SysDeptEntity();
            sysDeptEntity.setId(0L);
            sysDeptEntity.setParentId(0L);
            sysDeptEntity.setDeptLevel(0);
            sysDeptEntity.setDeptStatus(DeptStatusEnums.NORMAL);
            sysDeptEntity.setDeptSort(0);
            sysDeptEntity.setAncestors("0");
            return sysDeptEntity;
        }
        // @formatter:off
        LambdaQueryWrapper<SysDeptEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(
                SysDeptEntity::getId,
                SysDeptEntity::getParentId,
                SysDeptEntity::getDeptLevel,
                SysDeptEntity::getDeptStatus,
                SysDeptEntity::getDeptSort,
                SysDeptEntity::getAncestors);
        lambdaQueryWrapper.eq(SysDeptEntity::getId, parentId);
        // @formatter:on
        return getOne(lambdaQueryWrapper);
    }


}