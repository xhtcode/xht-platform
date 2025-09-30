package com.xht.system.modules.authority.service;

import java.util.List;

/**
 * 角色用户Service接口
 *
 * @author xht
 **/
public interface IUserRoleService {
    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId  用户ID
     * @param roleIds 角色ID
     */
    void userBindRole(Long userId, List<Long> roleIds);

    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> selectRoleIdByUserId(String userId);
}
