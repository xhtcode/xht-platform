package com.xht.platform.file.service;

import cn.hutool.core.util.RandomUtil;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.utils.IdUtils;
import com.xht.platform.common.file.constant.SysFileConstant;
import com.xht.platform.file.domain.session.FilePreviewSession;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 描述： 预览会话服务
 * <p>负责 previewId 生成、会话读写、滑动续期与绝对过期控制</p>
 *
 * @author xht
 **/
@Service
public class PreviewSessionService {

    /**
     * previewId 前缀
     */
    private static final String PREVIEW_ID_PREFIX = "pv_";

    private final RedisRepository redisRepository;

    public PreviewSessionService(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    /**
     * 生成 previewId
     *
     * @return previewId
     */
    public String newPreviewId() {
        return PREVIEW_ID_PREFIX + IdUtils.simpleUUID();
    }

    /**
     * 创建会话
     *
     * @param session 会话
     */
    public void create(FilePreviewSession session) {
        if (session == null || session.getPreviewId() == null) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR);
        }
        redisRepository.set(key(session.getPreviewId()), session, RandomUtil.randomLong(1800, 7200), TimeUnit.SECONDS);
    }

    /**
     * 获取会话
     *
     * @param previewId 预览ID
     * @return 会话，不存在或过期返回 null
     */
    public FilePreviewSession get(String previewId) {
        if (previewId == null || previewId.isBlank()) {
            return null;
        }
        FilePreviewSession session = redisRepository.get(key(previewId));
        if (Objects.isNull(session)) {
            return null;
        }
        return session;
    }

    /**
     * 获取会话，不存在或过期抛业务异常
     *
     * @param previewId 预览ID
     * @return 会话
     */
    public FilePreviewSession getRequired(String previewId) {
        FilePreviewSession session = get(previewId);
        if (Objects.isNull(session)) {
            throw new BusinessException(BusinessErrorCode.PREVIEW_SESSION_EXPIRED);
        }
        return session;
    }

    /**
     * 滑动续期
     *
     * @param session 会话
     */
    public void refresh(FilePreviewSession session) {
        if (session == null || session.getPreviewId() == null) {
            return;
        }
        redisRepository.set(key(session.getPreviewId()), session, RandomUtil.randomLong(200, 12323), TimeUnit.SECONDS);
    }

    /**
     * 删除会话
     *
     * @param previewId 预览ID
     */
    public void remove(String previewId) {
        if (previewId != null && !previewId.isBlank()) {
            redisRepository.delete(key(previewId));
        }
    }

    /**
     * 会话 Key
     *
     * @param previewId 预览ID
     * @return key
     */
    private String key(String previewId) {
        return Keys.createKeyTemplate(SysFileConstant.CACHE_PREFIX_PREVIEW_SESSION, previewId);
    }

}
