package com.xht.system.modules.user.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.api.system.user.dto.UserInfoDTO;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.enums.GenderEnums;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.code.UserErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.IdCardUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.secret.MD5Utils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.system.modules.authority.dao.SysRoleMenuDao;
import com.xht.system.modules.authority.dao.SysUserRoleDao;
import com.xht.system.modules.authority.domain.entity.SysRoleEntity;
import com.xht.system.modules.dept.domain.response.SysPostResponse;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.enums.UserTypeEnums;
import com.xht.system.modules.user.converter.SysUserConverter;
import com.xht.system.modules.user.converter.SysUserDetailConverter;
import com.xht.system.modules.user.dao.SysUserDao;
import com.xht.system.modules.user.dao.SysUserDetailDao;
import com.xht.system.modules.user.dao.SysUserPostDao;
import com.xht.system.modules.user.domain.entity.SysUserDetailEntity;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.form.SysUserBasicForm;
import com.xht.system.modules.user.domain.form.SysUserDetailBasicForm;
import com.xht.system.modules.user.domain.form.UpdatePwdFrom;
import com.xht.system.modules.user.domain.query.SysUserBasicQuery;
import com.xht.system.modules.user.domain.response.SysUserResponse;
import com.xht.system.modules.user.domain.vo.SysUserVO;
import com.xht.system.modules.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户Service实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final SysUserDao sysUserDao;

    private final SysUserPostDao sysUserPostDao;

    private final SysUserRoleDao sysUserRoleDao;

    private final SysRoleMenuDao sysRoleMenuDao;

    private final SysUserConverter sysUserConverter;

    private final SysUserDetailDao sysUserDetailDao;

    private final SysUserDetailConverter sysUserDetailConverter;


    /**
     * 用户注册
     *
     * @param userForm 用户创建请求对象
     */
    @Override
    public void create(SysUserBasicForm userForm) {
        SysUserDetailBasicForm detail = userForm.getDetail();
        String idCard = detail.getIdCard();
        String userPhone = userForm.getUserPhone();
        // 校验当前用户信息是否已经注册过
        boolean userPhoneExists = sysUserDao.checkUserPhoneExists(userPhone, null);
        ThrowUtils.throwIf(userPhoneExists, BusinessErrorCode.DATA_EXIST, "该手机号已注册过账号，若为本人操作，可直接登录或联系客服核实！");
        boolean userIdCardExists = sysUserDetailDao.checkUserIdCardExists(idCard, null);
        ThrowUtils.throwIf(userIdCardExists, BusinessErrorCode.DATA_EXIST, "该身份证号已注册过账号，若为本人操作，可直接登录或联系客服核实！");
        // 格式化数据类型
        SysUserEntity sysUser = formatUser(userForm);
        SysUserDetailEntity adminEntity = formatUser(detail, idCard, sysUser.getId());
        sysUserDetailDao.saveUserInfo(sysUser, adminEntity);
    }


    /**
     * 将表单对象转换为系统用户实体对象
     *
     * @param form 表单对象，用于接收前端传入的用户数据
     * @return 转换后的系统用户实体对象，如果转换失败则返回null
     */
    protected final SysUserEntity formatUser(SysUserBasicForm form) {
        SysUserEntity entity = new SysUserEntity();
        entity.setId(IdUtil.getSnowflakeNextId());
        entity.setUserType(UserTypeEnums.BUSINESS);
        entity.setUserName("虚假账号");
        entity.setNickName(form.getNickName());
        entity.setPassWord(MD5Utils.generateSignature("123456"));
        entity.setPassWordSalt(MD5Utils.generateSignature("123456"));
        entity.setUserStatus(UserStatusEnums.NORMAL);
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
    private SysUserDetailEntity formatUser(SysUserDetailBasicForm detail, String idCard, Long userId) {
        SysUserDetailEntity adminEntity = sysUserDetailConverter.toEntity(detail);
        LocalDate now = LocalDate.now();
        // 从身份证中提取性别和出生日期信息
        GenderEnums gender = IdCardUtils.getGender(idCard);
        LocalDate birthDate = IdCardUtils.getBirthday(idCard).orElse(now);
        // 设置用户基本信息
        adminEntity.setUserId(userId);
        adminEntity.setGender(gender);
        adminEntity.setBirthDate(birthDate);
        adminEntity.setAge(now.getYear() - birthDate.getYear());
        return adminEntity;
    }
    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    @Override
    public void removeByUserId(Long userId) {
        Boolean exists = sysUserDao.exists(SysUserEntity::getId, userId);
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_NOT_EXIST, "用户不存在");
        sysUserDao.removeById(userId);
        sysUserDetailDao.removeByUserId(userId);
    }

    /**
     * 更新用户信息
     *
     * @param userForm 用户更新请求对象
     */
    @Override
    public void update(SysUserBasicForm userForm) {
        Long userId = userForm.getId();
        SysUserDetailBasicForm detail = userForm.getDetail();
        String idCard = detail.getIdCard();
        String userPhone = userForm.getUserPhone();
        Boolean userExists = sysUserDao.exists(SysUserEntity::getId, userId);
        ThrowUtils.throwIf(!userExists, BusinessErrorCode.DATA_NOT_EXIST);
        Boolean userInfoExists = sysUserDetailDao.exists(SysUserDetailEntity::getUserId, userId);
        ThrowUtils.throwIf(!userInfoExists, BusinessErrorCode.DATA_NOT_EXIST);
        // 校验当前用户信息是否已经注册过
        boolean userPhoneExists = sysUserDao.checkUserPhoneExists(userPhone, userId);
        ThrowUtils.throwIf(userPhoneExists, BusinessErrorCode.DATA_EXIST, "该手机号已注册过账号，若为本人操作，可直接登录或联系客服核实！");
        boolean userIdCardExists = sysUserDetailDao.checkUserIdCardExists(idCard, userId);
        ThrowUtils.throwIf(userIdCardExists, BusinessErrorCode.DATA_EXIST, "该身份证号已注册过账号，若为本人操作，可直接登录或联系客服核实！");
        // 格式化数据类型
        SysUserEntity sysUser = formatUser(userForm);
        SysUserDetailEntity adminEntity = formatUser(detail, idCard, userId);
        sysUserDetailDao.updateUserInfo(sysUser, adminEntity);
    }

    /**
     * 根据ID查找用户
     *
     * @param userId 用户ID
     * @return 找到的用户对象，不存在时返回null
     */
    @Override
    public SysUserVO findByUserId(Long userId) {
        ThrowUtils.notNull(userId, "用户ID不能为空");
        SysUserVO sysUserVO = sysUserDao.findInfoByUserId(userId);
        ThrowUtils.notNull(sysUserVO, "查询不到用户信息!");
        List<SysPostResponse> deptPostVo = sysUserPostDao.getPostByUserId(userId);
        sysUserVO.setPostInfos(deptPostVo);
        sysUserVO.setPassWord(null);
        sysUserVO.setPassWordSalt(null);
        return sysUserVO;
    }

    /**
     * 根据查询条件分页查找用户
     *
     * @param query 用户查询请求对象
     * @return 用户对象分页结果
     */
    @Override
    public PageResponse<SysUserResponse> findPageList(SysUserBasicQuery query) {
        Page<SysUserEntity> sysUserEntityPage = sysUserDao.findPageList(PageTool.getPage(query), query);
        return sysUserConverter.toResponse(sysUserEntityPage);
    }

    /**
     * 重置密码
     *
     * @param userId 用户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long userId) {
        sysUserDao.updatePassword(userId, MD5Utils.generateSignature("123456"));
    }

    /**
     * 修改密码
     *
     * @param form 用户更新请求对象
     */
    @Override
    public void updatePassword(UpdatePwdFrom form) {
        // todo 登录现在未实现用户id获取不到，先写死
        Long userId = 1L;
        SysUserEntity sysUserEntity = sysUserDao.findOptionalById(userId).orElseThrow(() -> new BusinessException(UserErrorCode.DATA_NOT_EXIST, "用户不存在"));
        // todo 后期补充用户状态判断
        String oldPassword = form.getOldPassword();
        String newPassword = form.getNewPassword();
        String confirmPassword = form.getConfirmPassword();
        String md5OldPassword = MD5Utils.generateSignature(oldPassword);
        if (!StringUtils.equals(md5OldPassword, sysUserEntity.getPassWord())) {
            throw new BusinessException(UserErrorCode.PASSWORD_ERROR, "旧密码错误");
        }
        if (!StringUtils.equals(newPassword, confirmPassword)) {
            throw new BusinessException(UserErrorCode.PASSWORD_ERROR, "两次密码输入不一致");
        }
        sysUserDao.updatePassword(userId, MD5Utils.generateSignature(form.getNewPassword()));
    }

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     */
    @Override
    public void updateStatus(Long userId, UserStatusEnums status) {
        Boolean exists = sysUserDao.exists(SysUserEntity::getId, userId);
        ThrowUtils.throwIf(exists, UserErrorCode.DATA_NOT_EXIST, "用户不存在");
        sysUserDao.updateStatus(userId, status);
    }

    /**
     * 根据用户名和登录类型获取用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    @Override
    public UserInfoDTO loadUserByUsername(String username, LoginTypeEnums loginType) {
        if (Objects.isNull(loginType) || Objects.equals(loginType, LoginTypeEnums.WECHAT) || Objects.equals(loginType, LoginTypeEnums.QQ)) {
            return null;
        }
        UserInfoDTO userInfoDTO = sysUserDao.findByUsernameAndLoginType(username, loginType);
        if (Objects.isNull(userInfoDTO)) {
            return null;
        }
        List<SysRoleEntity> roles = sysUserRoleDao.findRoleListByUserId(userInfoDTO.getUserId());
        List<String> permissionCodes = sysRoleMenuDao.findPermissionCodeByUserId(userInfoDTO.getUserId());
        if (!CollectionUtils.isEmpty(roles)) {
            userInfoDTO.setRoleCodes(roles.stream().map(SysRoleEntity::getRoleCode).collect(Collectors.toList()));
        }else {
            userInfoDTO.setRoleCodes(Collections.emptyList());
        }
        if (!CollectionUtils.isEmpty(permissionCodes)) {
            userInfoDTO.setPermissionCodes(permissionCodes);
        }else {
            userInfoDTO.setPermissionCodes(Collections.emptyList());
        }
        return userInfoDTO;
    }

}
