package com.xht.modules.generate.dao.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.modules.generate.entity.GenTemplateEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 针对表【gen_template(代码生成器-代码生成模板表（区分文件类型）)】的数据库操作Mapper
 *
 * @author xht
 */
@Mapper
public interface GenTemplateMapper extends BaseMapperX<GenTemplateEntity> {

}




