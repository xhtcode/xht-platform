package com.xht.platform.system.dao.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import  com.xht.platform.system.domain.vo.SysUserVO;
import  com.xht.platform.system.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

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

}




