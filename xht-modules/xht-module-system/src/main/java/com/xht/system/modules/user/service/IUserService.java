package com.xht.system.modules.user.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.domain.request.UpdatePwdRequest;
import com.xht.system.modules.user.domain.request.UserFormRequest;
import com.xht.system.modules.user.domain.request.UserQueryRequest;
import com.xht.system.modules.user.domain.vo.SysUserVO;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author xht
 **/
public interface IUserService {

    /**
     * 用户注册
     *
     * @param formRequest 用户创建请求对象
     * @return 操作成功返回true，否则返回false
     */
    Boolean create(UserFormRequest formRequest);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 操作成功返回true，否则返回false
     */
    Boolean delete(Long id);

    /**
     * 根据ID批量删除用户
     *
     * @param ids 用户ID
     * @return 操作成功返回true，否则返回false
     */
    Boolean removeByIds(List<Long> ids);

    /**
     * 更新用户信息
     *
     * @param formRequest 用户更新请求对象
     * @return 操作成功返回true，否则返回false
     */
    Boolean update(UserFormRequest formRequest);

    /**
     * 根据ID查找用户
     *
     * @param id 用户ID
     * @return 找到的用户对象，不存在时返回null
     */
    SysUserVO findById(Long id);

    /**
     * 根据查询条件分页查找用户
     *
     * @param queryRequest 用户查询请求对象
     * @return 用户对象分页结果
     */
    PageResponse<SysUserVO> selectPage(UserQueryRequest queryRequest);

    /**
     * 重置密码
     *
     * @param userId 用户ID
     * @return 操作成功返回true，否则返回false
     */
    Boolean resetPassword(Long userId);

    /**
     * 修改密码
     *
     * @param formRequest 用户更新请求对象
     * @return 操作成功返回true，否则返回false
     */
    Boolean updatePassword(UpdatePwdRequest formRequest);

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     * @return 操作成功返回true，否则返回false
     */
    Boolean updateStatus(Long userId, UserStatusEnums status);

}
