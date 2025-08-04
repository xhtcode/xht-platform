package com.xht.system.modules.authority.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.authority.domain.entity.SysUserDeptPostEntity;
import com.xht.system.modules.dept.domain.vo.SysDeptPostVo;
import com.xht.system.modules.user.domain.vo.UserSimpleVo;

import java.util.List;

/**
 * 用户部门关系Dao
 *
 * @author xht
 **/
public interface SysUserDeptPostDao extends MapperRepository<SysUserDeptPostEntity> {

    /**
     * 根据部门ID查询用户简要信息
     *
     * @param deptId 部门ID
     * @return 用户简要信息列表
     */
    List<UserSimpleVo> findUserSimpleVoByDeptId(Long deptId);

    /**
     * 根据用户ID获取部门信息
     *
     * @param userId 用户ID
     * @return 部门信息
     */
    SysDeptPostVo getDeptPostByUserId(Long userId);

    /**
     * 根据用户ID、部门ID、岗位ID判断用户部门关系是否存在
     *
     * @param userId 用户id
     * @param deptId 部门id
     * @param postId 岗位id
     * @return true：存在，false：不存在
     */
    boolean existsUserDept(Long userId, Long deptId, Long postId);

    /**
     * 根据用户ID删除用户部门关系
     *
     * @param userId          用户ID
     */
    void removeByUserId(Long userId);
}
