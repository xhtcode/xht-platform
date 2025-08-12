package com.xht.generate.service.impl;

import com.xht.generate.converter.GenColumnInfoConverter;
import com.xht.generate.dao.GenColumnInfoDao;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.response.GenColumnInfoResponse;
import com.xht.generate.service.IGenColumnInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 字段信息管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenColumnInfoServiceImpl implements IGenColumnInfoService {

    private final GenColumnInfoDao genColumnInfoDao;

    private final GenColumnInfoConverter genColumnInfoConverter;

    /**
     * 根据ID查询字段信息
     *
     * @param id 字段信息ID
     * @return 字段信息信息
     */
    @Override
    public GenColumnInfoResponse getById(Long id) {
        return genColumnInfoConverter.toResponse(genColumnInfoDao.findById(id));
    }

    /**
     * 根据表id查询字段信息
     *
     * @param tableId 表id
     * @return 字段信息分页信息
     */
    @Override
    public List<GenColumnInfoResponse> listByTableId(String tableId) {
        List<GenColumnInfoEntity> page = genColumnInfoDao.findList(GenColumnInfoEntity::getTableId, tableId);
        return genColumnInfoConverter.toResponse(page);
    }

}
