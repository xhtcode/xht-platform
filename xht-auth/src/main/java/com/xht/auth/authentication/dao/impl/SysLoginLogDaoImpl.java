package com.xht.auth.authentication.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.auth.authentication.dao.SysLoginLogDao;
import com.xht.auth.authentication.dao.mapper.SysLoginLogMapper;
import com.xht.auth.authentication.entity.SysLoginLogEntity;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
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
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysLoginLogEntity, ?> getFieldId() {
        return SysLoginLogEntity::getId;
    }

}
