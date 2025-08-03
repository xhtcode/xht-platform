package com.xht.system.modules.user.service;

import com.xht.system.modules.dept.domain.vo.SysDeptPostVo;
import com.xht.system.modules.user.domain.request.UserBindDeptPostRequest;
import com.xht.system.modules.user.domain.vo.UserSimpleVo;

import java.util.List;

/**
 * 用户部门Service接口
 *
 * @author xht
 **/
public interface IUserDeptService {

    /**
     * 用户绑定部门岗位信息
     *
     * @param bindRequest 用户绑定部门岗位请求参数
     * @return true成功，false失败
     */
    Boolean userBindDept(List<UserBindDeptPostRequest> bindRequest);

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
