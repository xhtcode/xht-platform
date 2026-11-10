package com.xht.platform.file.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.platform.file.dao.SysFileConfigLogDao;
import com.xht.platform.file.dao.mapper.SysFileConfigLogMapper;
import com.xht.platform.file.entity.SysFileConfigLogEntity;
import org.springframework.stereotype.Repository;

/**
 * 描述： 文件配置变更日志数据访问层实现
 *
 * @author xht
 **/
@Repository
public class SysFileConfigLogDaoImpl extends MapperRepositoryImpl<SysFileConfigLogMapper, SysFileConfigLogEntity> implements SysFileConfigLogDao {

    @Override
    protected SFunction<SysFileConfigLogEntity, ?> getFieldId() {
        return SysFileConfigLogEntity::getId;
    }

}
