package com.xht.system.log.manager;

import com.xht.framework.mybatis.manager.BasicManager;
import com.xht.system.log.domian.entity.SysLogEntity;
import com.xht.system.log.mapper.SysLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 系统日志管理
 *
 * @author xht
 **/
@Slf4j
@Component
public class SysLogManager extends BasicManager<SysLogMapper, SysLogEntity> {
}
