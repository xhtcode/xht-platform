package com.xht.platform.file.dao.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.platform.file.dao.SysFileSettingDao;
import com.xht.platform.file.dao.mapper.SysFileSettingMapper;
import com.xht.platform.file.entity.SysFileSettingEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 描述： 文件运行时配置数据访问层实现
 *
 * @author xht
 **/
@Repository
public class SysFileSettingDaoImpl extends MapperRepositoryImpl<SysFileSettingMapper, SysFileSettingEntity> implements SysFileSettingDao {

    @Override
    public List<SysFileSettingEntity> findAll() {
        return list();
    }

    @Override
    public Optional<SysFileSettingEntity> findByKey(String settingKey) {
        return findOneOptional(SysFileSettingEntity::getSettingKey, settingKey);
    }

    @Override
    public int updateValue(String settingKey, String value, Integer version) {
        LambdaUpdateWrapper<SysFileSettingEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysFileSettingEntity::getSettingValue, value);
        wrapper.setSql("version = version + 1");
        wrapper.eq(SysFileSettingEntity::getSettingKey, settingKey);
        wrapper.eq(version != null, SysFileSettingEntity::getVersion, version);
        return getBaseMapper().update(null, wrapper);
    }

    @Override
    protected SFunction<SysFileSettingEntity, ?> getFieldId() {
        return SysFileSettingEntity::getId;
    }

}
