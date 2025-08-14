package com.xht.generate.service.impl;

import com.xht.framework.core.exception.BusinessException;
import com.xht.generate.dao.*;
import com.xht.generate.domain.request.GenCodeCoreRequest;
import com.xht.generate.helper.GenLogHelper;
import com.xht.generate.service.IGenCodeCoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成核心服务
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenCodeCoreServiceImpl implements IGenCodeCoreService {

    private final GenColumnInfoDao genColumnInfoDao;

    private final GenDataSourceDao genDataSourceDao;

    private final GenTableInfoDao genTableInfoDao;

    private final GenTemplateDao genTemplateDao;

    private final GenTemplateGroupDao genTemplateGroupDao;

    private final GenTypeMappingDao genTypeMappingDao;

    /**
     * 生成代码
     *
     * @param genCodeCoreRequest 代码生成核心请求参数
     */
    @Override
    public void generateCode(GenCodeCoreRequest genCodeCoreRequest) {
        Long groupId = 0L;
        Long groupTemplateSize = 0L;
        List<String> tableIds = new ArrayList<>();
        try {
            GenLogHelper.success(groupId, groupTemplateSize, tableIds);
        } catch (Exception e) {
            log.error("生成代码异常: {}", e.getMessage(), e);
            GenLogHelper.fail(groupId, groupTemplateSize, tableIds, String.format("生成代码异常:%s", e.getMessage()));
            throw new BusinessException(e.getMessage());
        }
    }

}
