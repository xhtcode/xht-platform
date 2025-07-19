package com.xht.system.modules.user.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.framework.mybatis.dao.BasicDao;
import com.xht.system.modules.dept.domain.vo.SysDeptPostVo;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.domain.entity.SysUserDeptEntity;
import com.xht.system.modules.user.domain.vo.UserSimpleVo;
import com.xht.system.modules.user.mapper.SysUserDeptMapper;
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
public class SysUserDeptDao extends BasicDao<SysUserDeptMapper, SysUserDeptEntity> {

    /**
     * 保存用户和部门关系
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUserDept(Long deptId, List<SysUserDeptEntity> userDeptEntities) {
        LambdaQueryWrapper<SysUserDeptEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDeptEntity::getDeptId, deptId);
        remove(queryWrapper);
        if (CollectionUtils.isEmpty(userDeptEntities)) {
            return true;
        }
        return saveBatch(userDeptEntities);
    }

    /**
     * 根据部门ID查询用户简要信息
     *
     * @param deptId 部门ID
     * @return 用户简要信息列表
     */
    public List<UserSimpleVo> findUserSimpleVoByDeptId(Long deptId) {
        return getBaseMapper().findUserSimpleVoByDeptId(deptId, UserStatusEnums.NORMAL);
    }

    /**
     * 根据用户ID获取部门信息
     *
     * @param userId 用户ID
     * @return 部门信息
     */
    public SysDeptPostVo getDeptPostByUserId(Long userId) {
        return getBaseMapper().getDeptPostByUserId(userId);
    }

    /**
     * 根据用户ID获取部门信息
     *
     * @param userId 用户ID
     * @return 部门信息
     */
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
    public boolean existsUserDept(Long userId, Long deptId, Long postId) {
        LambdaQueryWrapper<SysUserDeptEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDeptEntity::getUserId, userId);
        queryWrapper.eq(SysUserDeptEntity::getDeptId, deptId);
        queryWrapper.eq(SysUserDeptEntity::getPostId, postId);
        return exists(queryWrapper);
    }
}
