package com.xht.auth.security.web.authentication.qr.manager;

import com.xht.framework.cache.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 二维码票据管理器
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class QrTicketManager extends AbstractQrManager {

    private final RedisRepository redisRepository;



}
