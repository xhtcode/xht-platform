package com.xht.platform.file.service;

import cn.hutool.core.util.RandomUtil;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.utils.IdUtils;
import com.xht.platform.common.file.constant.SysFileConstant;
import com.xht.platform.file.domain.response.SysFilePreviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 描述： 预览会话服务
 * <p>负责 previewId 生成、会话读写、滑动续期与绝对过期控制</p>
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class PreviewSessionService {

    /**
     * previewId 前缀
     */
    private static final String PREVIEW_ID_PREFIX = "pv_";

    private final RedisRepository redisRepository;

    /**
     * 生成 previewId
     *
     * @return previewId
     */
    public String createPreviewId() {
        return PREVIEW_ID_PREFIX + IdUtils.simpleUUID();
    }

    /**
     * 创建会话
     *
     * @param sysFilePreviewResponse 会话
     */
    public void save(String previewId, SysFilePreviewResponse sysFilePreviewResponse) {
        redisRepository.set(key(previewId), sysFilePreviewResponse, RandomUtil.randomLong(1800, 7200), TimeUnit.SECONDS);
    }

    public SysFilePreviewResponse get(String previewId) {
        return redisRepository.get(key(previewId));
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
