package com.xht.generate.helper;

import com.xht.framework.core.exception.UtilException;
import com.xht.generate.domain.bo.GenCodeCoreBo;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.request.GenCodeCoreRequest;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.StringUtils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

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

        return result;
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
            result.add(new GenCodeCoreBo(item.getFilePathTemplate(), item.getContent()));
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
