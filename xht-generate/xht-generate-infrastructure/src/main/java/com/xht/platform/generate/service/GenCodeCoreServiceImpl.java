package com.xht.platform.generate.service;

import cn.hutool.core.io.IoUtil;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.utils.AssertUtils;
import com.xht.platform.generate.bo.GenCodeCoreBO;
import com.xht.platform.generate.bo.TableInfoBO;
import com.xht.platform.generate.converter.GenCodeCoreConverter;
import com.xht.platform.generate.domain.form.GenCodeCoreForm;
import com.xht.platform.generate.domain.vo.GenCodeCoreVO;
import com.xht.platform.table.dao.GenTableColumnDao;
import com.xht.platform.table.dao.GenTableColumnQueryDao;
import com.xht.platform.table.dao.GenTableDao;
import com.xht.platform.table.entity.GenTableColumnEntity;
import com.xht.platform.table.entity.GenTableColumnQueryEntity;
import com.xht.platform.table.entity.GenTableEntity;
import com.xht.platform.template.dao.GenTemplateDao;
import com.xht.platform.template.entity.GenTemplateEntity;
import com.xht.platform.utils.GenCodeHelper;
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

    private final GenCodeCoreConverter genCodeCoreConverter;

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
     * @return List<GenCodeCoreBO> 代码预览信息列表，包含各个代码文件的内容
     */
    @Override
    public List<GenCodeCoreVO> viewCode(GenCodeCoreForm genCodeCoreForm) {
        if (Objects.isNull(genCodeCoreForm) || genCodeCoreForm.getTableIds().isEmpty()) {
            return Collections.emptyList();
        }
        List<GenCodeCoreVO> result = new ArrayList<>();
        List<GenCodeCoreBO> codeCoreBOList = generateCodeOriginal(genCodeCoreForm);
        Map<String, List<GenCodeCoreBO>> collect = codeCoreBOList.stream().collect(Collectors.groupingBy(GenCodeCoreBO::getTableName));
        collect.forEach((k, v) -> {
            GenCodeCoreVO vo = new GenCodeCoreVO();
            vo.setTableName(k);
            vo.setCodes(genCodeCoreConverter.convert(v));
            result.add(vo);
        });
        return result;
    }

    /**
     * 预览代码
     * 根据请求参数预览将要生成的代码内容，返回代码预览信息列表
     *
     * @param genCodeCoreForm 代码生成核心请求参数，包含生成代码所需的配置信息
     * @return List<GenCodeCoreBO> 代码预览信息列表，包含各个代码文件的内容
     */
    public List<GenCodeCoreBO> generateCodeOriginal(GenCodeCoreForm genCodeCoreForm) {
        // 校验并获取基础数据
        List<Long> tableIds = genCodeCoreForm.getTableIds();
        List<GenTableEntity> tableEntities = tableInfoDao.findList(GenTableEntity::getId, tableIds);
        AssertUtils.notEmpty(tableEntities, "请选择要生成的表");
        // 查询查询字段
        List<GenTableColumnQueryEntity> columnQueryEntities = columnQueryDao.findByTableId(tableIds);
        // 按表ID进行分组
        Map<Long, List<GenTableColumnQueryEntity>> columnQueryMap = columnQueryEntities.stream().collect(Collectors.groupingBy(GenTableColumnQueryEntity::getTableId));
        // 按分组整理数据
        Map<Long, List<GenTableEntity>> tablesByGroup = tableEntities.stream().collect(Collectors.groupingBy(GenTableEntity::getGroupId));
        // 解析模板并生成代码
        List<GenCodeCoreBO> codeList = new ArrayList<>();
        for (Long groupId : tablesByGroup.keySet()) {
            List<GenTableEntity> genTableEntities = tablesByGroup.get(groupId);
            List<GenTemplateEntity> groupTemplates = templateDao.findList(GenTemplateEntity::getGroupId, groupId);
            try {
                // 解析模板定义
                final List<GenCodeCoreBO> originalList = GenCodeHelper.parseTemplates(groupTemplates);
                // 为每个表生成代码
                for (GenTableEntity table : genTableEntities) {
                    List<GenTableColumnEntity> tableColumns = columnInfoDao.findList(GenTableColumnEntity::getTableId, table.getId());
                    TableInfoBO tableInfoBO = new TableInfoBO(table, tableColumns, columnQueryMap.getOrDefault(table.getId(), Collections.emptyList()));
                    List<GenCodeCoreBO> codeCoreBOList = GenCodeHelper.generateCode(genCodeCoreForm, tableInfoBO, originalList);
                    codeList.addAll(codeCoreBOList);
                }
            } catch (Exception e) {
                log.error("代码生成失败: {}", e.getMessage(), e);
                throw new BusinessException("代码生成失败", e);
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
    private byte[] generateZipPackage(List<GenCodeCoreBO> codeList) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ZipOutputStream zipOut = new ZipOutputStream(byteOut, StandardCharsets.UTF_8)) {

            zipOut.setComment("小糊涂代码生成器");

            // 添加使用说明文件
            addZipEntry(zipOut, "使用说明(不能完全依赖生成的代码!).txt", "");

            // 添加生成的代码文件
            for (GenCodeCoreBO codeBO : codeList) {
                String filePath = codeBO.getFilePath();
                // 处理空白文件夹(确保文件夹路径以"/"结尾)
                if (filePath.endsWith("/")) {
                    // 空文件夹需要创建一个不带内容的条目
                    addZipEntry(zipOut, filePath, "");
                } else {
                    addZipEntry(zipOut, filePath, codeBO.getCode());
                }
            }
            zipOut.finish();
            return byteOut.toByteArray();

        } catch (Exception e) {
            log.error("代码打包为ZIP失败 {}", e.getMessage(), e);
            throw new BusinessException("代码生成失败", e);
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