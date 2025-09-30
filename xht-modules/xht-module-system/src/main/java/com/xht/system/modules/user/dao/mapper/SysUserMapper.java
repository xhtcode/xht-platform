package com.xht.system.modules.user.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.request.SysUserQuery;
import com.xht.system.modules.user.domain.vo.SysUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper
 *
 * @author xht
 */
@Mapper
public interface SysUserMapper extends BaseMapperX<SysUserEntity> {

    /**
     * 分页查询用户信息
     *
     * @param page         分页信息
     * @param query 查询请求参数
     * @return 分页查询结果
     */
    Page<SysUserVO> queryPageRequest(Page<SysUserEntity> page, @Param("queryRequest") SysUserQuery query);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserVO findInfoByUserId(Long userId);

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param userName  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    SysUserVO findByUsernameAndLoginType(@Param("userName") String userName, @Param("loginType") String loginType);

    /**
     * 根据手机号和身份证号校验用户是否重复
     *
     * @param neUserId     不包括的用户ID
     * @param phoneNumber  手机号
     * @param idCardNumber 身份证号
     * @return true：存在；false：不存在
     */
    Integer checkUserRepeat(@Param("neUserId") Long neUserId, @Param("phoneNumber") String phoneNumber, @Param("idCardNumber") String idCardNumber);
}




