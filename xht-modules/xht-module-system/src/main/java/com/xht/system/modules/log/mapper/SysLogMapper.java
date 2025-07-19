package com.xht.system.modules.log.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.system.modules.log.domian.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志管理
 *
 * @author xht
 **/
@Mapper
public interface SysLogMapper extends BaseMapperX<SysLogEntity> {
}
