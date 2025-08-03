package com.xht.system.modules.user.dao.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.system.modules.dept.domain.vo.SysDeptPostVo;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.domain.entity.SysUserDeptPostEntity;
import com.xht.system.modules.user.domain.vo.UserSimpleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户部门关系
 *
 * @author xht
 */
@Mapper
public interface SysUserDeptPostMapper extends BaseMapperX<SysUserDeptPostEntity> {

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




