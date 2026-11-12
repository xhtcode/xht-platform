package com.xht.platform.file.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.common.file.constant.SysFileConstant;
import com.xht.platform.file.dao.SysFileTypeDao;
import com.xht.platform.file.dao.mapper.SysFileTypeMapper;
import com.xht.platform.file.domain.form.SysFileTypeForm;
import com.xht.platform.file.domain.query.SysFileTypeQuery;
import com.xht.platform.file.entity.SysFileTypeEntity;
import com.xht.platform.file.enums.PreviewTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 描述： 文件预览类型配置数据访问层实现类
 *
 * @author xht
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class SysFileTypeDaoImpl extends MapperRepositoryImpl<SysFileTypeMapper, SysFileTypeEntity> implements SysFileTypeDao {

    private final RedisRepository redisRepository;

    /**
     * 分页查询文件预览类型配置
     *
     * @param page  分页对象
     * @param query 查询参数
     * @return 分页结果
     */
    @Override
    public Page<SysFileTypeEntity> findPageList(Page<SysFileTypeEntity> page, SysFileTypeQuery query) {
        LambdaQueryWrapper<SysFileTypeEntity> wrapper = buildQueryWrapper(query);
        wrapper.orderByAsc(SysFileTypeEntity::getId);
        return page(page, wrapper);
    }

    /**
     * 判断指定文件后缀是否已配置预览类型
     *
     * @param fileSuffix 文件后缀
     * @param excludeId  需要排除的配置ID（更新场景），可为 null
     * @return true：已存在；false：不存在
     */
    @Override
    public boolean existsBySuffix(String fileSuffix, Long excludeId) {
        LambdaQueryWrapper<SysFileTypeEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(fileSuffix)) {
            wrapper.eq(SysFileTypeEntity::getFileSuffix, fileSuffix);
        } else {
            wrapper.isNull(SysFileTypeEntity::getFileSuffix);
        }
        wrapper.ne(condition(excludeId), SysFileTypeEntity::getId, excludeId);
        return exists(wrapper);
    }

    /**
     * 根据文件后缀查询配置
     *
     * @param fileSuffix 文件后缀
     * @return {@link PreviewTypeEnum}
     */
    @Override
    public PreviewTypeEnum findBySuffix(String fileSuffix) {
        SysFileTypeEntity entity = findConfigBySuffix(fileSuffix);
        return Optional.ofNullable(entity).map(SysFileTypeEntity::getPreviewType).orElse(PreviewTypeEnum.OTHER);
    }

    /**
     * 根据文件后缀查询完整配置
     * <p>先查精确后缀，未命中再查 file_suffix 为空的默认行</p>
     *
     * @param fileSuffix 文件后缀
     * @return 配置实体，未命中返回 null
     */
    @Override
    public SysFileTypeEntity findConfigBySuffix(String fileSuffix) {
        String cacheKey = Keys.createKey(SysFileConstant.CACHE_PREFIX_FILE_TYPE, "config", StringUtils.emptyToDefault(fileSuffix, "__default__"));
        SysFileTypeEntity cached = redisRepository.get(cacheKey);
        if (Objects.nonNull(cached)) {
            return cached;
        }


        LambdaQueryWrapper<SysFileTypeEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(fileSuffix)) {
            wrapper.eq(SysFileTypeEntity::getFileSuffix, fileSuffix);
        } else {
            wrapper.isNull(SysFileTypeEntity::getFileSuffix);
        }
        SysFileTypeEntity entity = getOne(wrapper, false);

        if (Objects.isNull(entity) && StringUtils.hasText(fileSuffix)) {
            LambdaQueryWrapper<SysFileTypeEntity> defaultWrapper = new LambdaQueryWrapper<>();
            defaultWrapper.isNull(SysFileTypeEntity::getFileSuffix);
            entity = getOne(defaultWrapper);
        }
        if (Objects.nonNull(entity)) {
            redisRepository.set(cacheKey, entity, Keys.randomExpire(60, 120), TimeUnit.MINUTES);
        }
        return entity;
    }

    /**
     * 根据ID更新配置（仅更新表单中的字段，文件后缀允许置空）
     *
     * @param fileTypeId 配置ID
     * @param form       配置表单
     */
    @Override
    public void updateFormRequest(Long fileTypeId, SysFileTypeForm form) {
        LambdaUpdateWrapper<SysFileTypeEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysFileTypeEntity::getPreviewType, form.getPreviewType());
        wrapper.set(SysFileTypeEntity::getPreviewName, form.getPreviewName());
        wrapper.set(SysFileTypeEntity::getFileSuffix, form.getFileSuffix());
        wrapper.set(SysFileTypeEntity::getPreviewSource, form.getPreviewSource());
        wrapper.set(SysFileTypeEntity::getConvertTarget, form.getConvertTarget());
        wrapper.set(SysFileTypeEntity::getEnabled, form.getEnabled() == null ? 1 : form.getEnabled());
        wrapper.set(SysFileTypeEntity::getSort, form.getSort() == null ? 0 : form.getSort());
        wrapper.eq(SysFileTypeEntity::getId, fileTypeId);
        update(wrapper);
    }

    /**
     * 根据文件后缀删除缓存
     *
     * @param fileSuffix 文件后缀
     */
    @Override
    public void removeCache(String fileSuffix) {
        if (!StringUtils.hasText(fileSuffix)) {
            redisRepository.delete(Keys.createKeyTemplate(SysFileConstant.CACHE_PREFIX_FILE_TYPE, "config:__default__"));
            return;
        }
        redisRepository.delete(Keys.createKeyTemplate(SysFileConstant.CACHE_PREFIX_FILE_TYPE, fileSuffix));
        redisRepository.delete(Keys.createKeyTemplate(SysFileConstant.CACHE_PREFIX_FILE_TYPE, "config:" + fileSuffix));
        redisRepository.delete(Keys.createKeyTemplate(SysFileConstant.CACHE_PREFIX_FILE_TYPE, "config:__default__"));
    }

    /**
     * 构建动态查询条件
     *
     * @param query 查询参数
     * @return 查询包装器
     */
    private LambdaQueryWrapper<SysFileTypeEntity> buildQueryWrapper(SysFileTypeQuery query) {
        LambdaQueryWrapper<SysFileTypeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(condition(query.getPreviewType()), SysFileTypeEntity::getPreviewType, query.getPreviewType());
        wrapper.like(condition(query.getPreviewName()), SysFileTypeEntity::getPreviewName, query.getPreviewName());
        wrapper.eq(condition(query.getFileSuffix()), SysFileTypeEntity::getFileSuffix, query.getFileSuffix());
        return wrapper;
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysFileTypeEntity, ?> getFieldId() {
        return SysFileTypeEntity::getId;
    }

}
