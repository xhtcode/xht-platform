package com.xht.system.modules.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.dept.domain.vo.SysDeptPostVo;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.dao.SysUserDeptDao;
import com.xht.system.modules.user.dao.mapper.SysUserDeptMapper;
import com.xht.system.modules.user.domain.entity.SysUserDeptEntity;
import com.xht.system.modules.user.domain.vo.UserSimpleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户部门关系Dao
 *
 * @author xht
 **/
@Slf4j
@Component
public class SysUserDeptDaoImpl extends MapperRepositoryImpl<SysUserDeptMapper, SysUserDeptEntity> implements SysUserDeptDao {

    /**
     * 保存用户和部门关系
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUserDept(Long deptId, List<SysUserDeptEntity> userDeptEntities) {
        LambdaQueryWrapper<SysUserDeptEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDeptEntity::getDeptId, deptId);
        remove(queryWrapper);
        if (CollectionUtils.isEmpty(userDeptEntities)) {
            return true;
        }
        return saveAll(userDeptEntities);
    }

    /**
     * 根据部门ID查询用户简要信息
     *
     * @param deptId 部门ID
     * @return 用户简要信息列表
     */
    @Override
    public List<UserSimpleVo> findUserSimpleVoByDeptId(Long deptId) {
        return baseMapper.findUserSimpleVoByDeptId(deptId, UserStatusEnums.NORMAL);
    }

    /**
     * 根据用户ID获取部门信息
     *
     * @param userId 用户ID
     * @return 部门信息
     */
    @Override
    public SysDeptPostVo getDeptPostByUserId(Long userId) {
        return baseMapper.getDeptPostByUserId(userId);
    }

    /**
     * 根据用户ID获取部门信息
     *
     * @param userId 用户ID
     * @return 部门信息
     */
    @Override
    public SysUserDeptEntity findOneByUserId(Long userId) {
        LambdaQueryWrapper<SysUserDeptEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDeptEntity::getUserId, userId);
        return getOne(queryWrapper);
    }

    /**
     * 根据用户ID、部门ID、岗位ID判断用户部门关系是否存在
     *
     * @param userId 用户id
     * @param deptId 部门id
     * @param postId 岗位id
     * @return true：存在，false：不存在
     */
    @Override
    public boolean existsUserDept(Long userId, Long deptId, Long postId) {
        LambdaQueryWrapper<SysUserDeptEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDeptEntity::getUserId, userId);
        queryWrapper.eq(SysUserDeptEntity::getDeptId, deptId);
        queryWrapper.eq(SysUserDeptEntity::getPostId, postId);
        return exists(queryWrapper);
    }

    /**
     * 根据部门ID、旧领导用户ID、新领导岗位ID删除用户部门关系
     *
     * @param deptId          部门ID
     * @param oldLeaderUserId 旧领导用户ID
     * @param leaderPostId    新领导岗位ID
     */
    @Override
    public void deleteBy(Long deptId, Long oldLeaderUserId, Long leaderPostId) {
        // @formatter:off
        LambdaQueryWrapper<SysUserDeptEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SysUserDeptEntity::getDeptId, deptId)
                .eq(SysUserDeptEntity::getUserId, oldLeaderUserId)
                .eq(SysUserDeptEntity::getPostId, leaderPostId);
        // @formatter:on
        remove(queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysUserDeptEntity, ?> getFieldId() {
        return SysUserDeptEntity::getUserId;
    }

}
