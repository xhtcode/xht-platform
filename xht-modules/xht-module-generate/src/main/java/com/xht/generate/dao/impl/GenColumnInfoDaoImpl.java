package com.xht.generate.dao.impl;

import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenColumnInfoDao;
import com.xht.generate.dao.mapper.GenColumnInfoMapper;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 字段信息管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenColumnInfoDaoImpl extends MapperRepositoryImpl<GenColumnInfoMapper, GenColumnInfoEntity> implements GenColumnInfoDao {

}
