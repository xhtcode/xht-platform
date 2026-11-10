package com.xht.platform.file.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.platform.file.entity.SysFileSettingEntity;

import java.util.List;
import java.util.Optional;

/**
 * 描述： 文件运行时配置数据访问层
 *
 * @author xht
 **/
public interface SysFileSettingDao extends MapperRepository<SysFileSettingEntity> {

    /**
     * 查询全部配置
     *
     * @return 配置列表
     */
    List<SysFileSettingEntity> findAll();

    /**
     * 根据 key 查询
     *
     * @param settingKey 配置键
     * @return 配置
     */
    Optional<SysFileSettingEntity> findByKey(String settingKey);

    /**
     * 更新配置值
     *
     * @param settingKey 配置键
     * @param value      新值
     * @param version    期望版本
     * @return 影响行数
     */
    int updateValue(String settingKey, String value, Integer version);

}
