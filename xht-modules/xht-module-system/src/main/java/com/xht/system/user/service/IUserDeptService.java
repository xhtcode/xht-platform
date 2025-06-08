package com.xht.system.user.service;

import com.xht.system.dept.domain.vo.SysDeptPostVo;
import com.xht.system.user.domain.vo.UserSimpleVo;

import java.util.List;

/**
 * 用户部门Service接口
 *
 * @author xht
 **/
public interface IUserDeptService {
    /**
     * 部门分配用户
     *
     * @param deptId  部门ID
     * @param userIds 用户ID列表
     * @return 成功返回true，失败返回false
     */
    Boolean userBindDept(Long deptId, List<Long> userIds);

    /**
     * 根据部门ID获取已绑定用户ID列表
     *
     * @param deptId 部门ID
     * @return 已绑定用户ID列表
     */
    List<UserSimpleVo> getBindUserByDeptId(Long deptId);

    /**
     * 根据用户ID获取部门信息
     *
     * @param userId 用户ID
     * @return 部门信息
     */
    SysDeptPostVo getDeptByUserId(Long userId);
}
