package com.xht.framework.mybatis.mapper.common;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * 提供公共的工具Mapper
 *
 * @author xht
 **/
@Mapper
public interface UtilsMapper {

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    LocalDateTime getCurrentTime();

}
