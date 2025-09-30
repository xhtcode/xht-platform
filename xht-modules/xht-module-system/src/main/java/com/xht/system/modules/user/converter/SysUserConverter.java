package com.xht.system.modules.user.converter;

import com.xht.cloud.oauth2.dto.UserInfoDTO;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.entity.SysUserProfilesEntity;
import com.xht.system.modules.user.domain.response.SysUserProfilesResponse;
import com.xht.system.modules.user.domain.response.SysUserResponse;
import com.xht.system.modules.user.domain.vo.SysUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 用户信息转换器
 *
 * @author xht
 **/
@SuppressWarnings("unused")
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserConverter {
    /**
     * 将SysUserEntity对象转换为SysUserResponse对象。
     * 该方法通常用于在服务层与控制器层之间传递数据时，
     * 将包含完整用户信息的SysUserEntity对象转换为仅包含必要信息的SysUserResponse对象。
     *
     * @param sysUserEntity 包含用户详细信息的SysUserEntity对象
     * @return 包含用户基本信息的SysUserResponse对象
     */
    SysUserResponse toResponse(SysUserEntity sysUserEntity);

    /**
     * 将SysUserEntity对象转换为SysUserVO对象。
     * 该方法通常用于在业务逻辑层与表示层之间传递数据时，
     * 将包含完整用户信息的SysUserEntity对象转换为仅包含必要信息的SysUserVO对象。
     *
     * @param sysUserEntity 包含用户详细信息的SysUserEntity对象
     * @return 包含用户基本信息的SysUserVO对象
     */
    SysUserVO toVo(SysUserEntity sysUserEntity);

    /**
     * 将SysUserProfilesEntity对象转换为SysUserProfilesResponse对象。
     * 该方法通常用于在服务层与控制器层之间传递用户配置文件信息时，
     * 将包含完整配置文件信息的SysUserProfilesEntity对象转换为仅包含必要信息的SysUserProfilesResponse对象。
     *
     * @param profilesEntity 包含用户配置文件详细信息的SysUserProfilesEntity对象
     * @return 包含用户配置文件基本信息的SysUserProfilesResponse对象
     */
    SysUserProfilesResponse toResponse(SysUserProfilesEntity profilesEntity);

    /**
     * 将SysUserVO对象转换为UserInfoDTO对象
     *
     * @param sysUserVO 系统用户VO对象，包含用户的基本信息
     * @return UserInfoDTO 用户信息DTO对象，用于数据传输
     */
    UserInfoDTO convertToDto(SysUserVO sysUserVO);


}
