package com.xht.modules.system.converter;

import com.xht.api.system.domain.form.SysUserDetailForm;
import com.xht.api.system.domain.form.SysUserForm;
import com.xht.api.system.domain.response.SysUserDetailResponse;
import com.xht.api.system.domain.response.SysUserResponse;
import com.xht.api.system.domain.vo.SysUserVO;
import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.system.entity.SysUserDetailEntity;
import com.xht.modules.system.entity.SysUserEntity;
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
public interface SysUserConverter extends BasicConverter<SysUserEntity, SysUserForm, SysUserResponse> {

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
    SysUserDetailResponse toResponse(SysUserDetailEntity profilesEntity);

    /**
     * 将SysUserProfileForm对象转换为SysUserProfilesEntity对象
     *
     * @param profileForm 用户配置文件表单对象，包含用户配置文件的信息
     * @return SysUserDetailEntity 用户配置文件实体对象，用于数据存储
     */
    SysUserDetailEntity toEntity(SysUserDetailForm profileForm);
}
