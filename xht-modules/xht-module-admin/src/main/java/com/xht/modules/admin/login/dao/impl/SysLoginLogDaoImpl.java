package com.xht.modules.admin.login.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.login.dao.SysLoginLogDao;
import com.xht.modules.admin.login.dao.mapper.SysLoginLogMapper;
import com.xht.modules.admin.login.entity.SysLoginLogEntity;
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
