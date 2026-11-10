package com.xht.platform.file.service;

import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.utils.IdUtils;
import com.xht.platform.common.file.constant.SysFileConstant;
import com.xht.platform.file.domain.session.ShareTicket;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 描述： 分享票据服务
 *
 * @author xht
 **/
@Service
public class ShareTicketService {

    private static final String TICKET_PREFIX = "st_";

    /**
     * 票据 TTL：2 小时
     */
    private static final long TICKET_TTL_SECONDS = 7200L;

    private final RedisRepository redisRepository;

    public ShareTicketService(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    /**
     * 创建票据
     *
     * @param shareId    分享ID
     * @param fileId     文件ID
     * @param shareMode  分享模式
     * @param permission 分享权限
     * @param receiverId 接收人记录ID
     * @param codeVersion 提取码版本
     * @param shareExpireAt 分享过期时间
     * @return 票据
     */
    public ShareTicket create(Long shareId, Long fileId, com.xht.platform.file.enums.ShareModeEnum shareMode,
                              com.xht.platform.file.enums.SharePermissionEnum permission, Long receiverId,
                              Integer codeVersion, LocalDateTime shareExpireAt) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireAt = now.plusSeconds(TICKET_TTL_SECONDS);
        if (shareExpireAt != null && shareExpireAt.isBefore(expireAt)) {
            expireAt = shareExpireAt;
        }
        long ttl = Math.max(1L, java.time.Duration.between(now, expireAt).getSeconds());
        ShareTicket ticket = ShareTicket.builder()
                .ticket(TICKET_PREFIX + IdUtils.simpleUUID())
                .shareId(shareId)
                .fileId(fileId)
                .shareMode(shareMode)
                .permission(permission)
                .receiverId(receiverId)
                .codeVersion(codeVersion)
                .expireAt(expireAt)
                .build();
        redisRepository.set(key(ticket.getTicket()), ticket, ttl, TimeUnit.SECONDS);
        return ticket;
    }

    /**
     * 获取票据
     *
     * @param ticket 票据
     * @return 票据，不存在返回 null
     */
    public ShareTicket get(String ticket) {
        if (ticket == null || ticket.isBlank()) {
            return null;
        }
        ShareTicket shareTicket = redisRepository.get(key(ticket));
        if (shareTicket == null) {
            return null;
        }
        if (shareTicket.getExpireAt() != null && LocalDateTime.now().isAfter(shareTicket.getExpireAt())) {
            remove(ticket);
            return null;
        }
        return shareTicket;
    }

    /**
     * 删除票据
     *
     * @param ticket 票据
     */
    public void remove(String ticket) {
        if (ticket != null && !ticket.isBlank()) {
            redisRepository.delete(key(ticket));
        }
    }

    private String key(String ticket) {
        return Keys.createKeyTemplate(SysFileConstant.CACHE_PREFIX_SHARE_TICKET, ticket);
    }

}
