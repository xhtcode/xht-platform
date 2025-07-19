package com.xht.system.modules.log.dao;

import com.xht.framework.mybatis.dao.BasicDao;
import com.xht.system.modules.log.domian.entity.SysLogEntity;
import com.xht.system.modules.log.mapper.SysLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 系统日志管理
 *
 * @author xht
 **/
@Slf4j
@Component
public class SysLogDao extends BasicDao<SysLogMapper, SysLogEntity> {
}
