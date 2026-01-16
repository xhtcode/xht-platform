package com.xht.modules.admin.system.dao.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.modules.admin.system.domain.vo.SysUserVo;
import com.xht.modules.admin.system.entity.SysUserEntity;
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
    SysUserVo findInfoByUserId(Long userId);

}




