package com.xht.framework.mybatis.blog.mapper;

import com.xht.framework.core.support.blog.dto.BLogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author xht
 **/
@Mapper
public interface BLogMapper {

    /**
     * 保存 blog 日志
     *
     * @param id  主键
     * @param dto 日志 DTO
     */
    void saveLog(@Param("id") Long id, @Param("dto") BLogDTO dto);

}
