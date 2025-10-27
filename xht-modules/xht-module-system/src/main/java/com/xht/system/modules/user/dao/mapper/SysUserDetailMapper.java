package com.xht.system.modules.user.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.system.modules.user.domain.entity.SysUserDetailEntity;
import com.xht.system.modules.user.domain.request.SysUserQuery;
import com.xht.system.modules.user.domain.response.SysUserDetailResponse;
import com.xht.system.modules.user.domain.vo.SysUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xht
 */
@Mapper
public interface SysUserDetailMapper extends BaseMapperX<SysUserDetailEntity> {

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserVO<SysUserDetailResponse> findByUserId(@Param("userId") Long userId);

    /**
     * 根据用户账号和登录类型获取用户信息
     *
     * @param userName  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    SysUserVO<SysUserDetailResponse> loadUserByUserName(@Param("userName") String userName, @Param("loginType") String loginType);

    /**
     * 分页查询用户信息
     *
     * @param page  分页参数
     * @param query 查询参数
     * @return 用户信息
     */
    PageResponse<SysUserVO<SysUserDetailResponse>> findPageList(Page<SysUserDetailEntity> page, @Param("query") SysUserQuery query);
}




