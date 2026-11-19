package com.xht.platform.login.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.platform.login.entity.SysLoginLogEntity;

/**
 * 登录日志 Dao
 *
 * @author xht
 **/
public interface SysLoginLogDao extends MapperRepository<SysLoginLogEntity> {

    /**
     * 根据用户名查询登录日志
     *
     * @param page     分页参数
     * @param userName 用户名
     */
    Page<SysLoginLogEntity> findPageByUserName(Page<SysLoginLogEntity> page, String userName);
}
