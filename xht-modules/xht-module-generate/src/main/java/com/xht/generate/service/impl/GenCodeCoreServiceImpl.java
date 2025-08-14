package com.xht.generate.service.impl;

import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.generate.dao.*;
import com.xht.generate.domain.bo.GenCodeCoreBo;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.request.GenCodeCoreRequest;
import com.xht.generate.helper.GenCodeHelper;
import com.xht.generate.helper.GenLogHelper;
import com.xht.generate.service.IGenCodeCoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Long groupTemplateSize = 0L;
        List<GenCodeCoreBo> codeCoreList = new ArrayList<>();
        List<String> tableIds = genCodeCoreRequest.getTableIds();
        List<GenTableInfoEntity> tableInfoEntities = genTableInfoDao.findList(GenTableInfoEntity::getId, genCodeCoreRequest.getTableIds());
        ThrowUtils.notEmpty(tableInfoEntities, "请选择要生成的表");
        List<GenColumnInfoEntity> columnInfoEntities = genColumnInfoDao.findList(GenColumnInfoEntity::getTableId, genCodeCoreRequest.getTableIds());
        Map<Long, List<GenTableInfoEntity>> groupTableInfoEntities = tableInfoEntities.stream().collect(Collectors.groupingBy(GenTableInfoEntity::getGroupId));
        Map<String, List<GenColumnInfoEntity>> groupColumnInfoEntities = columnInfoEntities.stream().collect(Collectors.groupingBy(GenColumnInfoEntity::getTableId));
        List<GenTemplateEntity> templateEntities = genTemplateDao.findList(GenTemplateEntity::getGroupId, groupTableInfoEntities.keySet());
        Map<Long, List<GenTemplateEntity>> groupTemplateEntities = templateEntities.stream().collect(Collectors.groupingBy(GenTemplateEntity::getGroupId));
        for (Long groupId : groupTableInfoEntities.keySet()) {
            try {
                List<GenTemplateEntity> templateEntityList = groupTemplateEntities.get(groupId);
                List<GenCodeCoreBo> parse = GenCodeHelper.parse(templateEntityList);
                List<GenTableInfoEntity> entities = groupTableInfoEntities.get(groupId);
                for (GenTableInfoEntity tableInfo : entities) {
                    List<GenColumnInfoEntity> columnInfoEntityList = groupColumnInfoEntities.get(tableInfo.getId());
                    VelocityContext velocityContext = GenCodeHelper.generate(genCodeCoreRequest, tableInfo, columnInfoEntityList);
                    GenCodeHelper.generateCode(velocityContext, parse);
                    codeCoreList.addAll(parse);
                }
                GenLogHelper.success(groupId, groupTemplateSize, tableIds);
            } catch (Exception e) {
                log.error("生成代码异常: {}", e.getMessage(), e);
                GenLogHelper.fail(groupId, groupTemplateSize, tableIds, String.format("生成代码异常:%s", e.getMessage()));
                throw new BusinessException(e.getMessage());
            }
        }
    }

}
