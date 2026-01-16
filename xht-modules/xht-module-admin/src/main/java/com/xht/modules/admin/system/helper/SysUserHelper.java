package com.xht.modules.admin.system.helper;

import cn.hutool.core.util.IdUtil;
import com.xht.framework.core.enums.GenderEnums;
import com.xht.framework.core.utils.IdCardUtils;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.modules.admin.system.converter.SysUserDetailConverter;
import com.xht.modules.admin.system.entity.SysUserDetailEntity;
import com.xht.modules.admin.system.entity.SysUserEntity;
import com.xht.modules.admin.system.domain.form.SysUserForm;
import com.xht.modules.admin.system.domain.form.SysUserDetailForm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 用户帮助类
 *
 * @author xht
 **/
@Slf4j
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SysUserHelper implements ApplicationContextAware {

    private static SysUserDetailConverter sysUserDetailConverter;

    private static PasswordEncoder passwordEncoder;

    /**
     * 将表单对象转换为系统用户实体对象
     *
     * @param form 表单对象，用于接收前端传入的用户数据
     * @return 转换后的系统用户实体对象，如果转换失败则返回null
     */
    public static SysUserEntity formatUser(SysUserForm form) {
        SysUserEntity entity = new SysUserEntity();
        entity.setId(IdUtil.getSnowflakeNextId());
        if (SecurityUtils.isAdmin()) {
            entity.setUserName(form.getUserName());
            entity.setUserType(form.getUserType());
            entity.setUserStatus(form.getUserStatus());
        }
        entity.setNickName(form.getNickName());
        entity.setPassWord(passwordEncoder.encode("123456"));
        entity.setPassWordSalt(passwordEncoder.encode("123456"));
        entity.setUserPhone(form.getUserPhone());
        entity.setUserAvatar("/images/user/avatar.png");
        entity.setDeptId(form.getDeptId());
        entity.setDeptName(form.getDeptName());
        return entity;
    }


    /**
     * 格式化用户信息，将表单数据转换为实体对象并填充身份证相关信息
     *
     * @param detail 用户表单数据
     * @param idCard 身份证号码
     * @param userId 用户ID
     * @return 格式化后的用户实体对象
     */
    public static SysUserDetailEntity formatUser(SysUserDetailForm detail, String idCard, Long userId) {
        SysUserDetailEntity detailEntity = sysUserDetailConverter.toEntity(detail);
        LocalDate now = LocalDate.now();
        // 从身份证中提取性别和出生日期信息
        GenderEnums gender = IdCardUtils.getGender(idCard);
        LocalDate birthDate = IdCardUtils.getBirthday(idCard).orElse(now);
        // 设置用户基本信息
        detailEntity.setUserId(userId);
        detailEntity.setGender(Objects.requireNonNullElse(detail.getGender(), gender));
        detailEntity.setBirthDate(Objects.requireNonNullElse(detail.getBirthDate(), birthDate));
        detailEntity.setAge(Objects.requireNonNullElse(detail.getAge(), now.getYear() - birthDate.getYear()));
        return detailEntity;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        sysUserDetailConverter = applicationContext.getBean(SysUserDetailConverter.class);
        passwordEncoder = applicationContext.getBean(PasswordEncoder.class);
    }
}
