package com.xht.system.modules.user.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.cloud.oauth2.dto.UserInfoDTO;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.code.UserErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.secret.MD5Utils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.system.modules.authority.dao.SysRoleMenuDao;
import com.xht.system.modules.authority.dao.SysUserRoleDao;
import com.xht.system.modules.authority.domain.entity.SysRoleEntity;
import com.xht.system.modules.dept.dao.SysDeptDao;
import com.xht.system.modules.dept.domain.entity.SysDeptEntity;
import com.xht.system.modules.dept.domain.response.SysPostResponse;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.converter.SysUserConverter;
import com.xht.system.modules.user.dao.SysUserDao;
import com.xht.system.modules.user.dao.SysUserPostDao;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.entity.SysUserProfilesEntity;
import com.xht.system.modules.user.domain.request.UpdatePwdRequest;
import com.xht.system.modules.user.domain.request.UserFormRequest;
import com.xht.system.modules.user.domain.request.UserProfileFormRequest;
import com.xht.system.modules.user.domain.request.UserQueryRequest;
import com.xht.system.modules.user.domain.vo.SysUserVO;
import com.xht.system.modules.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    private final SysDeptDao sysDeptDao;

    /**
     * 获取用户信息实体
     *
     * @param formRequest 用户创建请求对象
     * @return 用户信息实体
     */
    private SysUserProfilesEntity getSysUserProfilesEntity(UserFormRequest formRequest) {
        SysUserProfilesEntity result = new SysUserProfilesEntity();
        UserProfileFormRequest userProfile = formRequest.getUserProfile();
        result.setRealName(userProfile.getRealName());
        result.setIdCardNumber(userProfile.getIdCardNumber());
        result.setGender(userProfile.getGender());
        result.setBirthDate(userProfile.getBirthDate());
        result.setAge(userProfile.getAge());
        result.setAddress(userProfile.getAddress());
        result.setPostalCode(userProfile.getPostalCode());
        return result;
    }

    /**
     * 获取用户实体
     *
     * @param formRequest 用户创建请求对象
     * @return 用户实体
     */
    private SysUserEntity getSysUserEntity(UserFormRequest formRequest) {
        SysUserEntity result = new SysUserEntity();
        result.setUserType(formRequest.getUserType());
        result.setUserName(formRequest.getUserName());
        result.setPhoneNumber(formRequest.getPhoneNumber());
        result.setUserStatus(formRequest.getUserStatus());
        result.setDeptId(formRequest.getDeptId());
        return result;
    }


    /**
     * 用户注册
     *
     * @param formRequest 用户创建请求对象
     * @return 操作成功返回true，否则返回false
     */
    @Override
    public Boolean create(UserFormRequest formRequest) {
        SysUserEntity sysUser = getSysUserEntity(formRequest);
        sysUser.setUserAccount(IdUtil.fastUUID());
        sysUser.setPassWord(MD5Utils.generateSignature("123456"));
        sysUser.setPassWordSalt(MD5Utils.generateSignature("123456"));
        sysUser.setAvatarUrl("/images/user/avatar.png");
        ThrowUtils.throwIf(sysDeptDao.exists(SysDeptEntity::getId, formRequest.getDeptId()), BusinessErrorCode.DATA_NOT_EXIST, "部门不存在");
        SysUserProfilesEntity userProfiles = getSysUserProfilesEntity(formRequest);
        Boolean checkUserRepeat = sysUserDao.checkUserRepeat(null, sysUser.getPhoneNumber(), userProfiles.getIdCardNumber());
        ThrowUtils.throwIf(checkUserRepeat, BusinessErrorCode.DATA_EXIST, "手机号或身份证号码已存在");
        return sysUserDao.saveUserInfo(sysUser, userProfiles);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 操作成功返回true，否则返回false
     */
    @Override
    public Boolean delete(Long id) {
        Boolean exists = sysUserDao.exists(SysUserEntity::getId, id);
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_NOT_EXIST, "用户不存在");
        return sysUserDao.removeUserInfo(id);
    }

    /**
     * 根据ID批量删除用户
     *
     * @param ids 用户ID
     * @return 操作成功返回true，否则返回false
     */
    @Override
    public Boolean removeByIds(List<Long> ids) {
        ThrowUtils.notNull(ids, BusinessErrorCode.PARAM_ERROR);
        return sysUserDao.deleteAllById(ids);
    }

    /**
     * 更新用户信息
     *
     * @param formRequest 用户更新请求对象
     * @return 操作成功返回true，否则返回false
     */
    @Override
    public Boolean update(UserFormRequest formRequest) {
        SysUserEntity dbUser = sysUserDao.findById(formRequest.getId());
        ThrowUtils.notNull(dbUser, BusinessErrorCode.DATA_NOT_EXIST);
        SysUserEntity sysUserEntity = getSysUserEntity(formRequest);
        SysUserProfilesEntity userProfiles = getSysUserProfilesEntity(formRequest);
        ThrowUtils.throwIf(sysDeptDao.exists(SysDeptEntity::getId, formRequest.getDeptId()), BusinessErrorCode.DATA_NOT_EXIST, "部门不存在");
        Boolean checkUserRepeat = sysUserDao.checkUserRepeat(sysUserEntity.getId(), sysUserEntity.getPhoneNumber(), userProfiles.getIdCardNumber());
        ThrowUtils.throwIf(checkUserRepeat, BusinessErrorCode.DATA_EXIST, "手机号或身份证号码已存在");
        return sysUserDao.updateUserInfo(sysUserEntity, userProfiles);
    }

    /**
     * 根据ID查找用户
     *
     * @param userId 用户ID
     * @return 找到的用户对象，不存在时返回null
     */
    @Override
    public SysUserVO findByUserId(Long userId) {
        SysUserVO sysUserVO = sysUserDao.findInfoByUserId(userId);
        List<SysPostResponse> deptPostVo = sysUserPostDao.getPostByUserId(userId);
        sysUserVO.setPost(deptPostVo);
        sysUserVO.setPassWord(null);
        sysUserVO.setPassWordSalt(null);
        return sysUserVO;
    }

    /**
     * 根据查询条件分页查找用户
     *
     * @param queryRequest 用户查询请求对象
     * @return 用户对象分页结果
     */
    @Override
    public PageResponse<SysUserVO> selectPage(UserQueryRequest queryRequest) {
        Page<SysUserVO> page = sysUserDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return PageTool.getPageVo(page);
    }

    /**
     * 重置密码
     *
     * @param userId 用户ID
     * @return 操作成功返回true，否则返回false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean resetPassword(Long userId) {
        return sysUserDao.updatePassword(userId, MD5Utils.generateSignature("123456"));
    }

    /**
     * 修改密码
     *
     * @param formRequest 用户更新请求对象
     * @return 操作成功返回true，否则返回false
     */
    @Override
    public Boolean updatePassword(UpdatePwdRequest formRequest) {
        // todo 登录现在未实现用户id获取不到，先写死
        Long userId = 1L;
        SysUserEntity sysUserEntity = sysUserDao.findOptionalById(userId).orElseThrow(() -> new BusinessException(UserErrorCode.DATA_NOT_EXIST, "用户不存在"));
        // todo 后期补充用户状态判断
        String oldPassword = formRequest.getOldPassword();
        String newPassword = formRequest.getNewPassword();
        String confirmPassword = formRequest.getConfirmPassword();
        String md5OldPassword = MD5Utils.generateSignature(oldPassword);
        if (!StringUtils.equals(md5OldPassword, sysUserEntity.getPassWord())) {
            throw new BusinessException(UserErrorCode.PASSWORD_ERROR, "旧密码错误");
        }
        if (!StringUtils.equals(newPassword, confirmPassword)) {
            throw new BusinessException(UserErrorCode.PASSWORD_ERROR, "两次密码输入不一致");
        }
        return sysUserDao.updatePassword(userId, MD5Utils.generateSignature(formRequest.getNewPassword()));
    }

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     * @return 操作成功返回true，否则返回false
     */
    @Override
    public Boolean updateStatus(Long userId, UserStatusEnums status) {
        Boolean exists = sysUserDao.exists(SysUserEntity::getId, userId);
        ThrowUtils.throwIf(exists, UserErrorCode.DATA_NOT_EXIST, "用户不存在");
        return sysUserDao.updateStatus(userId, status);
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
        SysUserVO sysUserVO = sysUserDao.findByUsernameAndLoginType(username, loginType);
        if (Objects.isNull(sysUserVO)) {
            return null;
        }
        List<SysRoleEntity> roles = sysUserRoleDao.findRoleListByUserId(sysUserVO.getId());
        List<String> permissionCodes = sysRoleMenuDao.findPermissionCodeByUserId(sysUserVO.getId());
        UserInfoDTO userInfoDTO = sysUserConverter.convertToDto(sysUserVO);
        if (!CollectionUtils.isEmpty(roles)) {
            userInfoDTO.setRoleCodes(roles.stream().map(SysRoleEntity::getRoleCode).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(permissionCodes)) {
            userInfoDTO.setPermissionCodes(permissionCodes);
        }
        return userInfoDTO;
    }
}
