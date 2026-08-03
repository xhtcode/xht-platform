package com.xht.workflow.flowable.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 描述： 流程模型 Mapper 接口
 *
 * @author xht
 **/
@Mapper
public interface ModelMapper {

    /**
     * 检查流程模型标识是否存在
     *
     * @param modelKey       流程模型标识
     * @param excludeModelId 需要排除的流程模型ID（编辑时传自身ID，新增传null）
     * @return true：存在；false：不存在
     */
    boolean checkModelKeyExist(@Param("modelKey") String modelKey, @Param("excludeModelId") String excludeModelId);

    /**
     * 检查流程模型名称是否存在
     *
     * @param modelName      流程模型名称
     * @param excludeModelId 需要排除的流程模型ID
     * @return true：存在；false：不存在
     */
    boolean checkModelNameExist(@Param("modelName") String modelName, @Param("excludeModelId") String excludeModelId);

    /**
     * 检查模型key或名称任意一个重复（批量校验）
     *
     * @param modelKey       模型Key
     * @param modelName      模型名称
     * @param excludeModelId 排除自身ID
     * @return true 任意一个重复
     */
    boolean checkModelKeyAndNameExist(@Param("modelKey") String modelKey,
                                      @Param("modelName") String modelName,
                                      @Param("excludeModelId") String excludeModelId);
}
