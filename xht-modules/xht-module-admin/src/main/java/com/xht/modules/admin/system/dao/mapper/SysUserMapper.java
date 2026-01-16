package com.xht.modules.admin.system.dao.mapper;

import com.xht.modules.admin.system.domain.vo.SysUserVO;
import com.xht.modules.admin.system.domain.vo.UserLoginVo;
import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.modules.admin.system.entity.SysUserEntity;
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
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserVO findInfoByUserId(Long userId);

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param userName  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    UserLoginVo findByUsernameAndLoginType(@Param("userName") String userName, @Param("loginType") String loginType);

}




