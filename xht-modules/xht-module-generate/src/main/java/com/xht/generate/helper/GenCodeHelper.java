package com.xht.generate.helper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.UtilException;
import com.xht.framework.core.utils.StringUtils;
import com.xht.generate.constant.GenConstant;
import com.xht.generate.domain.bo.GenCodeCoreBo;
import com.xht.generate.domain.bo.TableInfoBo;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.form.GenCodeCoreForm;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.CollectionUtils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 代码生成参数帮助类
 * 负责处理代码生成过程中的参数转换、Velocity上下文构建和模板解析
 *
 * @author xht
 **/
public final class GenCodeHelper {

    /**
     * 预生成的ID和UUID数量
     */
    private static final int ID_GENERATOR_COUNT = 10;

    /**
     * 私有构造器，禁止实例化
     */
    private GenCodeHelper() {
        throw new UtilException("工具类不允许实例化");
    }

    /**
     * 生成代码文件
     *
     * @param request     代码生成核心请求参数
     * @param tableInfoBo 表信息业务对象
     * @param codeCore    解析后的代码核心业务对象列表
     */
    public static List<GenCodeCoreBo> generateCode(GenCodeCoreForm request, TableInfoBo tableInfoBo, List<GenCodeCoreBo> codeCore) {
        List<GenCodeCoreBo> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(codeCore)) {
            return Collections.emptyList();
        }
        VelocityContext context = new VelocityContext();
        context.put("packageName", request.getPackageName());
        context.put("nowDate", DateUtil.now());
        addIdGenerators(context);
        for (GenCodeCoreBo item : codeCore) {
            Set<String> ignoreField = item.getIgnoreField();
            tableInfoBo.fillVelocityContext(context, ignoreField);
            // 渲染文件路径
            String resolvedPath = renderTemplate(context, "filePathTemplate", item.getFilePath());
            // 渲染文件路径
            String fileName = renderTemplate(context, "fileName", item.getFileName());
            // 渲染文件内容
            String resolvedCode = renderTemplate(context, "codeTemplate", item.getCode());
            GenCodeCoreBo genCodeCoreBo = new GenCodeCoreBo(item.getIgnoreField());
            genCodeCoreBo.setFilePath(String.format("%s.%s", StringUtils.replace(resolvedPath, GenConstant.POINT, GenConstant.PATH_SEPARATOR), item.getFileType()));
            genCodeCoreBo.setFileName(fileName);
            genCodeCoreBo.setTableName(tableInfoBo.getTableName());
            genCodeCoreBo.setCode(resolvedCode);
            genCodeCoreBo.setFileType(item.getFileType());
            result.add(genCodeCoreBo);
        }
        return result;
    }



    /**
     * 向上下文添加ID和UUID生成器
     */
    private static void addIdGenerators(VelocityContext context) {
        for (int i = 0; i < ID_GENERATOR_COUNT; i++) {
            int index = i + 1;
            context.put("id" + index, IdUtil.getSnowflakeNextId());
            context.put("uuid" + index, IdUtil.fastSimpleUUID());
        }
    }

    /**
     * 解析模板列表为代码核心业务对象列表
     *
     * @param templateList 模板实体列表
     * @return 代码核心业务对象列表，包含文件路径和模板内容
     */
    public static List<GenCodeCoreBo> parseTemplates(List<GenTemplateEntity> templateList) {
        if (CollectionUtils.isEmpty(templateList)) {
            throw new BusinessException("模板列表不能为空");
        }
        return templateList.stream()
                .map((template) -> {
                    GenCodeCoreBo genCodeCoreBo = new GenCodeCoreBo(template.getTemplateIgnoreField());
                    genCodeCoreBo.setFilePath(String.format("%s%s", template.getTemplateFilePath(), template.getTemplateFileName()));
                    genCodeCoreBo.setFileName(String.format("%s.%s", template.getTemplateFileName(), template.getTemplateFileType()));
                    genCodeCoreBo.setCode(template.getTemplateContent());
                    genCodeCoreBo.setFileType(template.getTemplateFileType());
                    return genCodeCoreBo;
                })
                .collect(Collectors.toList());
    }


    /**
     * 使用Velocity模板引擎渲染模板内容
     *
     * @param context         Velocity上下文
     * @param templateName    模板名称（用于日志和错误提示）
     * @param templateContent 模板内容
     * @return 渲染后的字符串
     */
    private static String renderTemplate(VelocityContext context, String templateName, String templateContent) {
        if (!StringUtils.hasText(templateName) || !StringUtils.hasText(templateContent)) {
            return StrUtil.EMPTY;
        }
        try (StringWriter writer = new StringWriter()) {
            Velocity.evaluate(context, writer, templateName, templateContent);
            return writer.toString();
        } catch (Exception e) {
            throw new UtilException("模板渲染失败，模板名称：" + templateName, e);
        }
    }

}
