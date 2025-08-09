package com.xht.generate.service.impl;

import com.xht.generate.converter.GenProjectTemplateConverter;
import com.xht.generate.dao.GenProjectTemplateDao;
import com.xht.generate.domain.entity.GenProjectTemplateEntity;
import com.xht.generate.domain.request.GenProjectTemplateFormRequest;
import com.xht.generate.service.IGenProjectTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 项目模板管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenProjectTemplateServiceImpl implements IGenProjectTemplateService {

    private final GenProjectTemplateDao genProjectTemplateDao;

    private final GenProjectTemplateConverter genProjectTemplateConverter;

    /**
     * 创建项目模板
     *
     * @param formRequest 项目模板表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(GenProjectTemplateFormRequest formRequest) {
        GenProjectTemplateEntity entity = genProjectTemplateConverter.toEntity(formRequest);
        return genProjectTemplateDao.saveTransactional(entity);
    }


}
