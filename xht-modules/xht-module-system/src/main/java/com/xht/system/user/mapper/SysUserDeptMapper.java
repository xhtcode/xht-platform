package com.xht.system.user.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.system.dept.domain.vo.SysDeptPostVo;
import com.xht.system.user.common.enums.UserStatusEnums;
import com.xht.system.user.domain.entity.SysUserDeptEntity;
import com.xht.system.user.domain.vo.UserSimpleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户部门关系
 *
 * @author xht
 */
@Mapper
public interface SysUserDeptMapper extends BaseMapperX<SysUserDeptEntity> {

    /**
     * 根据部门ID查询用户简要信息
     *
     * @param deptId 部门ID
     * @param status 用户状态
     * @return 用户简要信息
     */
    List<UserSimpleVo> findUserSimpleVoByDeptId(Long deptId, UserStatusEnums status);

    /**
     * 根据用户ID查询部门岗位信息
     *
     * @param userId 用户ID
     * @return 部门岗位信息
     */
    SysDeptPostVo getDeptPostByUserId(Long userId);
}




