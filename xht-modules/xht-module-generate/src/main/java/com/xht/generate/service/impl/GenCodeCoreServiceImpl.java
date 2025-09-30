package com.xht.generate.service.impl;

import cn.hutool.core.io.IoUtil;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.generate.dao.GenTableColumnDao;
import com.xht.generate.dao.GenTableDao;
import com.xht.generate.dao.GenTemplateDao;
import com.xht.generate.domain.bo.GenCodeCoreBo;
import com.xht.generate.domain.entity.GenTableColumnEntity;
import com.xht.generate.domain.entity.GenTableEntity;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.request.GenCodeCoreRequest;
import com.xht.generate.helper.GenCodeHelper;
import com.xht.generate.service.IGenCodeCoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成核心服务
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenCodeCoreServiceImpl implements IGenCodeCoreService {

    private final GenTableColumnDao columnInfoDao;

    private final GenTableDao tableInfoDao;

    private final GenTemplateDao templateDao;

    /**
     * 生成代码
     * 根据请求参数生成对应的代码文件，并返回字节数组格式的代码内容
     *
     * @param request 代码生成核心请求参数，包含生成代码所需的配置信息
     * @return byte[] 生成的代码文件字节数组
     */
    @Override
    public byte[] generateCode(GenCodeCoreRequest request) {
        return generateZipPackage(viewCode(request));
    }

    /**
     * 预览代码
     * 根据请求参数预览将要生成的代码内容，返回代码预览信息列表
     *
     * @param genCodeCoreRequest 代码生成核心请求参数，包含生成代码所需的配置信息
     * @return List<GenCodeCoreBo> 代码预览信息列表，包含各个代码文件的内容
     */
    @Override
    public List<GenCodeCoreBo> viewCode(GenCodeCoreRequest genCodeCoreRequest) {
        // 1. 校验并获取基础数据
        List<String> tableIds = genCodeCoreRequest.getTableIds();
        List<GenTableEntity> tableEntities = tableInfoDao.findList(GenTableEntity::getId, tableIds);
        ThrowUtils.notEmpty(tableEntities, "请选择要生成的表");
        // 2. 按分组整理数据
        Map<Long, List<GenTableEntity>> tablesByGroup = tableEntities.stream().collect(Collectors.groupingBy(GenTableEntity::getGroupId));
        // 3. 解析模板并生成代码
        List<GenCodeCoreBo> codeList = new ArrayList<>();
        for (Long groupId : tablesByGroup.keySet()) {
            List<GenTableEntity> genTableEntities = tablesByGroup.get(groupId);
            List<GenTemplateEntity> groupTemplates = templateDao.findList(GenTemplateEntity::getGroupId, groupId);
            try {
                // 解析模板定义
                final List<GenCodeCoreBo> originalList = GenCodeHelper.parseTemplates(groupTemplates);
                // 为每个表生成代码
                for (GenTableEntity table : genTableEntities) {
                    List<GenTableColumnEntity> tableColumns = columnInfoDao.findList(GenTableColumnEntity::getTableId, table.getId());
                    List<GenCodeCoreBo> codeCoreBoList = GenCodeHelper.generateCode(genCodeCoreRequest, table, tableColumns, originalList);
                    codeList.addAll(codeCoreBoList);
                }
            } catch (Exception e) {
                String errorMsg = String.format("模板分组id: %s 代码生成失败: %s", groupId, e.getMessage());
                log.error(errorMsg, e);
                throw new BusinessException(errorMsg);
            }
        }
        return codeList;
    }

    /**
     * 将生成的代码打包为ZIP文件
     *
     * @param codeList 代码生成业务对象列表
     * @return ZIP文件字节数组
     */
    private byte[] generateZipPackage(List<GenCodeCoreBo> codeList) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ZipOutputStream zipOut = new ZipOutputStream(byteOut, StandardCharsets.UTF_8)) {

            zipOut.setComment("小糊涂代码生成器");

            // 添加使用说明文件
            addZipEntry(zipOut, "使用说明(不能完全依赖生成的代码!).txt", "");

            // 添加生成的代码文件
            for (GenCodeCoreBo codeBo : codeList) {
                String filePath = codeBo.getFilePath();
                // 处理空白文件夹(确保文件夹路径以"/"结尾)
                if (filePath.endsWith("/")) {
                    // 空文件夹需要创建一个不带内容的条目
                    addZipEntry(zipOut, filePath, "");
                } else {
                    addZipEntry(zipOut, filePath, codeBo.getCode());
                }
            }
            zipOut.finish();
            return byteOut.toByteArray();

        } catch (Exception e) {
            log.error("代码打包为ZIP失败 {}", e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    /**
     * 向ZIP输出流添加一个条目
     *
     * @param zipOut    ZIP输出流
     * @param entryName 条目名称/路径
     * @param content   条目内容
     */
    private void addZipEntry(ZipOutputStream zipOut, String entryName, String content) throws Exception {
        zipOut.putNextEntry(new ZipEntry(entryName));
        IoUtil.write(zipOut, StandardCharsets.UTF_8, false, content);
        zipOut.closeEntry();
    }
}