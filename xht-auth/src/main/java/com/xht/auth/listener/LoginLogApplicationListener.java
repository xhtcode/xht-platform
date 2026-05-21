package com.xht.auth.listener;

import com.xht.auth.authentication.converter.SysLoginLogConverter;
import com.xht.auth.authentication.dao.SysLoginLogDao;
import com.xht.auth.authentication.entity.SysLoginLogEntity;
import com.xht.framework.log.event.LoginLogApplicationEvent;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 *
 * 登录日志监听器
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginLogApplicationListener implements ApplicationListener<LoginLogApplicationEvent> {

    private final SysLoginLogDao sysLoginLogDao;

    private final SysLoginLogConverter sysLoginLogConverter;


    @Override
    public void onApplicationEvent(@Nonnull LoginLogApplicationEvent event) {
        log.info("用户{}登录{}", event.getUserName(), event.getLoginStatus().getDescription());
        SysLoginLogEntity convert = sysLoginLogConverter.convert(event);
        sysLoginLogDao.save(convert);
    }

}
