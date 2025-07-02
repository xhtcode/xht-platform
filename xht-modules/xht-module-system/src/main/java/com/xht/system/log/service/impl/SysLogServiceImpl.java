package com.xht.system.log.service.impl;

import com.xht.system.log.converter.SysLogConverter;
import com.xht.system.log.manager.SysLogManager;
import com.xht.system.log.service.ISysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统日志管理
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl implements ISysLogService {

    private final SysLogConverter sysLogConverter;

    private final SysLogManager sysLogManager;

}
