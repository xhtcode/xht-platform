package com.xht.modules.admin.area.dao.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.area.dao.SysAreaDao;
import com.xht.modules.admin.area.dao.mapper.SysAreaMapper;
import com.xht.modules.admin.area.entity.SysAreaEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统管理 - 行政区划
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysAreaDaoImpl extends MapperRepositoryImpl<SysAreaMapper, SysAreaEntity> implements SysAreaDao {

    /**
     * 根据上级区划编码查询子区划
     *
     * @param parentCode 上级区划编码
     * @return 子区划列表
     */
    @Override
    public List<SysAreaEntity> listByParentCode(String parentCode) {
        LambdaUpdateWrapper<SysAreaEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysAreaEntity::getParentAreaCode, parentCode);
        wrapper.orderByAsc(SysAreaEntity::getAreaCode);
        return baseMapper.selectList(wrapper);
    }


    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysAreaEntity, ?> getFieldId() {
        return SysAreaEntity::getAreaCode;
    }

}
