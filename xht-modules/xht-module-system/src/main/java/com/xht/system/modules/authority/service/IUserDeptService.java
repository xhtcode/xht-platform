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
     * 获取已绑定的用户信息
     *
     * @param deptId 部门ID，可选参数，用于筛选指定部门下的已绑定用户
     * @return 返回部门下已绑定的用户信息列表，封装在R对象中
     */
    List<UserSimpleVo> getBindUserByDeptId(Long deptId);

}
