package com.xht.platform.file.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.dao.mapper.SysFileMapper;
import com.xht.platform.file.entity.SysFileEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 描述： 系统文件数据访问层实现类
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysFileDaoImpl extends MapperRepositoryImpl<SysFileMapper, SysFileEntity> implements SysFileDao {
    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysFileEntity, ?> getFieldId() {
        return SysFileEntity::getId;
    }
}
