package com.xht.system.modules.authority.service;

import com.xht.framework.core.utils.tree.INode;
import com.xht.system.modules.authority.domain.vo.AuthorityUserVO;

import java.util.List;

/**
 * 权限管理
 *
 * @author xht
 **/
public interface IAuthorityService {

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    AuthorityUserVO getUserProfileInfo();


    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    List<INode<Long>> getRouters();

}
