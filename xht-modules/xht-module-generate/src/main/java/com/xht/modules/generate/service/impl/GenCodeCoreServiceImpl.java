package com.xht.modules.generate.service.impl;

import cn.hutool.core.io.IoUtil;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.modules.generate.dao.GenTableColumnDao;
import com.xht.modules.generate.dao.GenTableColumnQueryDao;
import com.xht.modules.generate.dao.GenTableDao;
import com.xht.modules.generate.dao.GenTemplateDao;
import com.xht.modules.generate.domain.bo.GenCodeCoreBo;
import com.xht.modules.generate.domain.bo.TableInfoBo;
import com.xht.modules.generate.domain.entity.GenTableColumnEntity;
import com.xht.modules.generate.domain.entity.GenTableColumnQueryEntity;
import com.xht.modules.generate.domain.entity.GenTableEntity;
import com.xht.modules.generate.domain.entity.GenTemplateEntity;
import com.xht.modules.generate.domain.form.GenCodeCoreForm;
import com.xht.modules.generate.domain.vo.GenCodeCoreVo;
import com.xht.modules.helper.GenCodeHelper;
import com.xht.modules.generate.service.IGenCodeCoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

    private final GenTableDao tableInfoDao;

    private final GenTableColumnDao columnInfoDao;

    private final GenTableColumnQueryDao columnQueryDao;

    private final GenTemplateDao templateDao;

    /**
     * 生成代码
     * 根据请求参数生成对应的代码文件，并返回字节数组格式的代码内容
     *
     * @param request 代码生成核心请求参数，包含生成代码所需的配置信息
     * @return byte[] 生成的代码文件字节数组
     */
    @Override
    public byte[] generateCode(GenCodeCoreForm request) {
        return generateZipPackage(generateCodeOriginal(request));
    }

    /**
     * 预览代码
     * 根据请求参数预览将要生成的代码内容，返回代码预览信息列表
     *
     * @param genCodeCoreForm 代码生成核心请求参数，包含生成代码所需的配置信息
     * @return List<GenCodeCoreBo> 代码预览信息列表，包含各个代码文件的内容
     */
    @Override
    public List<GenCodeCoreVo> viewCode(GenCodeCoreForm genCodeCoreForm) {
        if (Objects.isNull(genCodeCoreForm) || genCodeCoreForm.getTableIds().isEmpty()) {
            return Collections.emptyList();
        }
        List<GenCodeCoreVo> result = new ArrayList<>();
        List<GenCodeCoreBo> codeCoreBoList = generateCodeOriginal(genCodeCoreForm);
        Map<String, List<GenCodeCoreBo>> collect = codeCoreBoList.stream().collect(Collectors.groupingBy(GenCodeCoreBo::getTableName));
        collect.forEach((k, v) -> {
            GenCodeCoreVo vo = new GenCodeCoreVo();
            vo.setTableName(k);
            vo.setCodes(v);
            result.add(vo);
        });
        return result;
    }

    /**
     * 预览代码
     * 根据请求参数预览将要生成的代码内容，返回代码预览信息列表
     *
     * @param genCodeCoreForm 代码生成核心请求参数，包含生成代码所需的配置信息
     * @return List<GenCodeCoreBo> 代码预览信息列表，包含各个代码文件的内容
     */
    public List<GenCodeCoreBo> generateCodeOriginal(GenCodeCoreForm genCodeCoreForm) {
        // 校验并获取基础数据
        List<Long> tableIds = genCodeCoreForm.getTableIds();
        List<GenTableEntity> tableEntities = tableInfoDao.findList(GenTableEntity::getId, tableIds);
        ThrowUtils.notEmpty(tableEntities, "请选择要生成的表");
        // 查询查询字段
        List<GenTableColumnQueryEntity> columnQueryEntities = columnQueryDao.findByTableId(tableIds);
        // 按表ID进行分组
        Map<Long, List<GenTableColumnQueryEntity>> columnQueryMap = columnQueryEntities.stream().collect(Collectors.groupingBy(GenTableColumnQueryEntity::getTableId));
        // 按分组整理数据
        Map<Long, List<GenTableEntity>> tablesByGroup = tableEntities.stream().collect(Collectors.groupingBy(GenTableEntity::getGroupId));
        // 解析模板并生成代码
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
                    TableInfoBo tableInfoBo = new TableInfoBo(table, tableColumns, columnQueryMap.getOrDefault(table.getId(), Collections.emptyList()));
                    List<GenCodeCoreBo> codeCoreBoList = GenCodeHelper.generateCode(genCodeCoreForm, tableInfoBo, originalList);
                    codeList.addAll(codeCoreBoList);
                }
            } catch (Exception e) {
                log.error("代码生成失败: {}", e.getMessage(), e);
                throw new BusinessException(e);
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