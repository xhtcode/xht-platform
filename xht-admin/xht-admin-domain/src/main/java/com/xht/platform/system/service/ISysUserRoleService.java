package com.xht.platform.system.service;

import com.xht.platform.system.domain.vo.SysUserRoleBindVo;

import java.util.List;

/**
 * 角色用户Service接口
 *
 * @author xht
 **/
public interface ISysUserRoleService {

    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId  用户ID
     * @param roleIds 角色ID
     */
    void userBindRole(Long userId, List<Long> roleIds);

    /**
     * 获取当前用户拥有的角色ID列表
     *
     * @param userId 用户ID
     * @return 用户角色绑定信息VO
     */
    SysUserRoleBindVo findBindRoleIds(Long userId);

}
