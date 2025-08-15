package com.xht.generate.service.impl;

import cn.hutool.core.io.IoUtil;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.generate.dao.GenColumnInfoDao;
import com.xht.generate.dao.GenTableInfoDao;
import com.xht.generate.dao.GenTemplateDao;
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

    private final GenColumnInfoDao columnInfoDao;

    private final GenTableInfoDao tableInfoDao;

    private final GenTemplateDao templateDao;

    /**
     * 生成代码并打包为ZIP字节数组
     *
     * @param request 代码生成核心请求参数
     * @return 生成的ZIP文件字节数组
     */
    @Override
    public byte[] generateCode(GenCodeCoreRequest request) {
        // 1. 校验并获取基础数据
        List<String> tableIds = request.getTableIds();
        List<GenTableInfoEntity> tableEntities = tableInfoDao.findList(GenTableInfoEntity::getId, tableIds);
        ThrowUtils.notEmpty(tableEntities, "请选择要生成的表");

        // 2. 按分组整理数据
        Map<Long, List<GenTableInfoEntity>> tablesByGroup = tableEntities.stream()
                .collect(Collectors.groupingBy(GenTableInfoEntity::getGroupId));

        List<GenColumnInfoEntity> columnEntities = columnInfoDao.findList(GenColumnInfoEntity::getTableId, tableIds);
        Map<String, List<GenColumnInfoEntity>> columnsByTable = columnEntities.stream()
                .collect(Collectors.groupingBy(GenColumnInfoEntity::getTableId));

        List<GenTemplateEntity> templateEntities = templateDao.findList(GenTemplateEntity::getGroupId, tablesByGroup.keySet());
        Map<Long, List<GenTemplateEntity>> templatesByGroup = templateEntities.stream()
                .collect(Collectors.groupingBy(GenTemplateEntity::getGroupId));

        // 3. 解析模板并生成代码
        List<GenCodeCoreBo> codeList = new ArrayList<>();
        for (Map.Entry<Long, List<GenTableInfoEntity>> groupEntry : tablesByGroup.entrySet()) {
            Long groupId = groupEntry.getKey();
            List<GenTableInfoEntity> groupTables = groupEntry.getValue();
            List<GenTemplateEntity> groupTemplates = templatesByGroup.getOrDefault(groupId, new ArrayList<>());
            try {
                // 解析模板定义
                List<GenCodeCoreBo> parsedTemplates = GenCodeHelper.parseTemplates(groupTemplates);
                // 为每个表生成代码
                for (GenTableInfoEntity table : groupTables) {
                    List<GenColumnInfoEntity> tableColumns = columnsByTable.getOrDefault(table.getId(), new ArrayList<>());
                    VelocityContext context = GenCodeHelper.buildVelocityContext(request, table, tableColumns);
                    GenCodeHelper.generateCode(context, parsedTemplates);
                    codeList.addAll(parsedTemplates);
                }
                GenLogHelper.success(groupId, groupTemplates.size(), tableIds);
                log.info("代码生成成功 [分组ID: {}, 表数量: {}]", groupId, groupTables.size());
            } catch (Exception e) {
                String errorMsg = String.format("分组ID: %s 代码生成失败: %s", groupId, e.getMessage());
                log.error(errorMsg, e);
                GenLogHelper.fail(groupId, groupTemplates.size(), tableIds, errorMsg);
                throw new BusinessException(errorMsg);
            }
        }

        // 4. 打包生成的代码为ZIP
        return generateZipPackage(codeList);
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
            String errorMsg = "代码打包为ZIP失败: " + e.getMessage();
            log.error(errorMsg, e);
            throw new BusinessException(errorMsg);
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