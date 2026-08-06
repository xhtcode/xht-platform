package com.xht.platform.table.service;

import com.xht.platform.table.converter.GenTableColumnConverter;
import com.xht.platform.table.dao.GenTableColumnDao;
import com.xht.platform.table.entity.GenTableColumnEntity;
import com.xht.platform.table.domain.response.GenTableColumnResponse;
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
public class GenTableColumnServiceImpl implements IGenTableColumnService {

    private final GenTableColumnDao genTableColumnDao;

    private final GenTableColumnConverter genTableColumnConverter;

    /**
     * 根据ID查询字段信息
     *
     * @param id 字段信息ID
     * @return 字段信息信息
     */
    @Override
    public GenTableColumnResponse findById(Long id) {
        return genTableColumnConverter.toResponse(genTableColumnDao.findById(id));
    }

    /**
     * 根据表id查询字段信息
     *
     * @param tableId 表id
     * @return 字段信息分页信息
     */
    @Override
    public List<GenTableColumnResponse> listByTableId(String tableId) {
        List<GenTableColumnEntity> page = genTableColumnDao.findList(GenTableColumnEntity::getTableId, tableId);
        return genTableColumnConverter.toResponse(page);
    }

}
