package com.xht.generate.helper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.constant.StringConstant;
import com.xht.framework.core.exception.UtilException;
import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.domain.ColumnExtConfig;
import com.xht.generate.domain.bo.GenCodeCoreBo;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.request.GenCodeCoreRequest;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.StringUtils;

import java.io.StringWriter;
import java.util.*;

/**
 * 代码生成参数帮助类
 *
 * @author xht
 **/
public final class GenCodeHelper {

    /**
     * 私有构造器，禁止实例化
     */
    private GenCodeHelper() {
        throw new UtilException("工具类不允许实例化");
    }

    /**
     * 生成Velocity上下文对象
     *
     * @param genCodeCoreRequest   代码生成核心请求参数
     * @param genTableInfoEntity   表信息实体
     * @param columnInfoEntityList 列信息实体列表
     * @return Velocity上下文对象
     */
    public static VelocityContext generate(GenCodeCoreRequest genCodeCoreRequest, GenTableInfoEntity genTableInfoEntity, List<GenColumnInfoEntity> columnInfoEntityList) {
        VelocityContext result = new VelocityContext();
        result.put("author", genCodeCoreRequest.getAuthor());
        result.put("packageName", genCodeCoreRequest.getPackageName());
        result.put("nowDate", DateUtil.now());
        result.put("table", convertToMap(genTableInfoEntity));
        List<Map<String, Object>> columns = new ArrayList<>();
        for (GenColumnInfoEntity item : columnInfoEntityList) {
            columns.add(convertToMap(item));
        }
        result.put("columns", columns);
        for (int i = 0; i < 10; i++) {
            result.put("id" + (i + 1), IdUtil.getSnowflakeNextId());
            result.put("uuid" + (i + 1), IdUtil.fastSimpleUUID());
        }
        return result;
    }

    private static Map<String, Object> convertToMap(GenTableInfoEntity entity) {
        if (Objects.isNull(entity)) {
            return Collections.emptyMap();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", entity.getId());
        map.put("groupId", entity.getGroupId());
        map.put("dataSourceId", entity.getDataSourceId());
        map.put("engineName", entity.getEngineName());
        map.put("tableName", entity.getTableName());
        map.put("tableComment", entity.getTableComment());
        map.put("codeName", entity.getCodeName());
        map.put("codeComment", entity.getCodeComment());
        map.put("tableCreateTime", entity.getTableCreateTime());
        map.put("tableUpdateTime", entity.getTableUpdateTime());
        return map;
    }

    private static Map<String, Object> convertToMap(GenColumnInfoEntity entity) {
        if (Objects.isNull(entity)) {
            return Collections.emptyMap();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", entity.getId());
        map.put("tableId", entity.getTableId());
        map.put("columnName", entity.getColumnName());
        map.put("dbDataType", entity.getDbDataType());
        map.put("columnComment", entity.getColumnComment());
        map.put("columnLength", entity.getColumnLength());
        map.put("codeName", entity.getCodeName());
        map.put("codeComment", entity.getCodeComment());
        // 存储枚举的实际值，而非枚举对象本身
        map.put("isRequired", Objects.requireNonNullElse(entity.getIsRequired(), GenStatusEnums.NO).getValue());
        map.put("isPrimary", Objects.requireNonNullElse(entity.getIsPrimary(), GenStatusEnums.NO).getValue());
        map.put("extConfig", entity.getExtConfig());
        map.put("sortOrder", entity.getSortOrder());
        ColumnExtConfig extConfig = Objects.requireNonNullElse(entity.getExtConfig(), new ColumnExtConfig());
        map.put("extFormItem", Objects.requireNonNullElse(extConfig.getFormItem(), GenStatusEnums.NO).getValue());
        map.put("extFormRequired", Objects.requireNonNullElse(extConfig.getFormRequired(), GenStatusEnums.NO).getValue());
        map.put("extFormType", extConfig.getFormType());
        map.put("extFormValidator", Objects.requireNonNullElse(extConfig.getFormValidator(), GenStatusEnums.NO).getValue());
        map.put("extGridItem", Objects.requireNonNullElse(extConfig.getGridItem(), GenStatusEnums.NO).getValue());
        map.put("extGridSort", Objects.requireNonNullElse(extConfig.getGridSort(), GenStatusEnums.NO).getValue());
        map.put("extQueryItem", Objects.requireNonNullElse(extConfig.getQueryItem(), GenStatusEnums.NO).getValue());
        map.put("extQueryType", extConfig.getQueryType());
        map.put("extQueryFormType", extConfig.getQueryFormType());
        map.put("extConfig", extConfig);
        return map;
    }

    /**
     * 解析模板列表为代码核心业务对象列表
     *
     * @param templateList 模板实体列表
     * @return 代码核心业务对象列表
     */
    public static List<GenCodeCoreBo> parse(List<GenTemplateEntity> templateList) {
        List<GenCodeCoreBo> result = new ArrayList<>();
        for (GenTemplateEntity item : templateList) {
            String filePathTemplate = StrUtil.removeSuffix(item.getFilePathTemplate(), StringConstant.SEPARATOR_SLASH);
            String fileNameTemplate = StrUtil.removePrefix(item.getFileNameTemplate(), StringConstant.SEPARATOR_SLASH);
            result.add(new GenCodeCoreBo(filePathTemplate + fileNameTemplate, item.getContent()));
        }
        return result;
    }

    /**
     * 根据Velocity上下文和代码核心业务对象列表生成代码
     *
     * @param velocityContext Velocity上下文
     * @param genCodeCoreList 代码核心业务对象列表
     */
    public static void generateCode(VelocityContext velocityContext, List<GenCodeCoreBo> genCodeCoreList) {
        for (GenCodeCoreBo item : genCodeCoreList) {
            item.setFilePath(generateCode(velocityContext, item.getFilePath(), item.getCode()));
            item.setCode(generateCode(velocityContext, item.getFilePath(), item.getCode()));
        }
    }

    /**
     * 使用Velocity模板引擎生成代码
     *
     * @param velocityContext Velocity上下文
     * @param telName         模板名称
     * @param code            模板代码
     * @return 生成的代码字符串
     */
    private static String generateCode(VelocityContext velocityContext, String telName, String code) {
        if (!StringUtils.hasText(telName) || !StringUtils.hasText(code)) return "";
        StringWriter stringWriter = new StringWriter();
        Velocity.evaluate(velocityContext, stringWriter, telName, code);
        return stringWriter.toString();
    }

}
