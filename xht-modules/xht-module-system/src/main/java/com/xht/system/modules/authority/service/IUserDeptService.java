package com.xht.system.modules.authority.service;

import com.xht.system.modules.authority.domain.request.UserBindDeptPostRequest;
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
    Boolean userBindDept(UserBindDeptPostRequest bindRequest);

    /**
     * 根据部门ID获取已绑定用户ID列表
     *
     * @param deptId 部门ID
     * @return 已绑定用户ID列表
     */
    List<UserSimpleVo> getBindUserByDeptId(Long deptId);

}
