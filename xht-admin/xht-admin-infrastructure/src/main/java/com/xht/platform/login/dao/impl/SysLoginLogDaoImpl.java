package com.xht.platform.login.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.platform.login.dao.SysLoginLogDao;
import com.xht.platform.login.dao.mapper.SysLoginLogMapper;
import com.xht.platform.login.entity.SysLoginLogEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 登录日志 Dao实现类
 *
 * @author xht
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class SysLoginLogDaoImpl extends MapperRepositoryImpl<SysLoginLogMapper, SysLoginLogEntity> implements SysLoginLogDao {

    /**
     * 根据用户名查询登录日志
     *
     * @param page     分页参数
     * @param userName 用户名
     */
    @Override
    public Page<SysLoginLogEntity> findPageByUserName(Page<SysLoginLogEntity> page, String userName) {
        LambdaQueryWrapper<SysLoginLogEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysLoginLogEntity::getUserName, userName);
        queryWrapper.orderByDesc(SysLoginLogEntity::getLoginTime);
        return page(page, queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysLoginLogEntity, ?> getFieldId() {
        return SysLoginLogEntity::getId;
    }

}
