package com.xht.system.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.system.user.domain.entity.SysUserEntity;
import com.xht.system.user.domain.request.UserQueryRequest;
import com.xht.system.user.domain.vo.SysUserVO;
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
     * @param queryRequest 查询条件
     * @return 分页查询结果
     */
    Page<SysUserVO> findPage(Page<SysUserEntity> page, @Param("queryRequest") UserQueryRequest queryRequest);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserVO findInfoByUserId(Long userId);
}




