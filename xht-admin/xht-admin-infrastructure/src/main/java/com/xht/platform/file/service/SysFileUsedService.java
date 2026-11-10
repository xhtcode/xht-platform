package com.xht.platform.file.service;

import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.utils.AssertUtils;
import com.xht.platform.common.file.constant.SysFileConstant;
import com.xht.platform.file.dao.SysFileUsedDao;
import com.xht.platform.file.entity.SysFileUsedEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 描述： 文件用途配置服务
 * <p>运行时用途校验与缓存；管理端修改用途后调用 refresh 清理缓存</p>
 *
 * @author xht
 **/
@Service
public class SysFileUsedService {

    /**
     * 用途缓存时长（分钟）
     */
    private static final long CACHE_MINUTES = 10L;

    private final SysFileUsedDao sysFileUsedDao;

    private final RedisRepository redisRepository;

    public SysFileUsedService(SysFileUsedDao sysFileUsedDao, RedisRepository redisRepository) {
        this.sysFileUsedDao = sysFileUsedDao;
        this.redisRepository = redisRepository;
    }

    /**
     * 根据编码查询用途
     *
     * @param usedCode 用途编码
     * @return 用途配置，不存在返回 null
     */
    public SysFileUsedEntity findByCode(String usedCode) {
        if (usedCode == null || usedCode.isBlank()) {
            return null;
        }
        String key = Keys.createKeyTemplate(SysFileConstant.CACHE_PREFIX_FILE_USED, usedCode);
        SysFileUsedEntity cached = redisRepository.get(key);
        if (Objects.nonNull(cached)) {
            return cached;
        }
        SysFileUsedEntity entity = sysFileUsedDao.findByCode(usedCode).orElse(null);
        if (Objects.nonNull(entity)) {
            redisRepository.set(key, entity, CACHE_MINUTES, TimeUnit.MINUTES);
        }
        return entity;
    }

    /**
     * 获取启用用途，不存在或停用时抛业务异常
     *
     * @param usedCode 用途编码
     * @return 用途配置
     */
    public SysFileUsedEntity getEnabledByCode(String usedCode) {
        SysFileUsedEntity entity = findByCode(usedCode);
        AssertUtils.notNull(entity, BusinessErrorCode.FILE_USED_NOT_EXIST);
        AssertUtils.isTrue(Objects.equals(entity.getEnabled(), 1), BusinessErrorCode.FILE_USED_NOT_EXIST);
        return entity;
    }

    /**
     * 查询全部启用用途
     *
     * @return 用途列表
     */
    public List<SysFileUsedEntity> listEnabled() {
        return sysFileUsedDao.findAllEnabled();
    }

    /**
     * 清理指定用途缓存
     *
     * @param usedCode 用途编码
     */
    public void refresh(String usedCode) {
        if (usedCode == null || usedCode.isBlank()) {
            return;
        }
        redisRepository.delete(Keys.createKeyTemplate(SysFileConstant.CACHE_PREFIX_FILE_USED, usedCode));
    }

}
