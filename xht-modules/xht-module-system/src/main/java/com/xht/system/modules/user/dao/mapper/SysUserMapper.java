package com.xht.system.modules.user.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.request.UserQueryRequest;
import com.xht.system.modules.user.domain.vo.SysUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper
 *
 * @author xht
 */
@Mapper
public interface SysUserMapper extends BaseMapperX<SysUserEntity> {
    /**
     * 分页查询用户信息
     *
     * @param page         分页信息
     * @param queryRequest 查询请求参数
     * @return 分页查询结果
     */
    Page<SysUserVO> queryPageRequest(Page<SysUserEntity> page, @Param("queryPageRequest") UserQueryRequest queryRequest);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserVO findInfoByUserId(Long userId);

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    SysUserVO findByUsernameAndLoginType(String username, LoginTypeEnums loginType);
}




