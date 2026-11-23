package com.xht.platform.system.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.common.enums.UserStatusEnum;
import com.xht.framework.common.enums.UserTypeEnum;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.utils.PassWordUtils;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.system.converter.SysUserConverter;
import com.xht.platform.system.dao.SysUserDao;
import com.xht.platform.system.dao.SysUserDetailDao;
import com.xht.platform.system.domain.form.SysUserDetailForm;
import com.xht.platform.system.domain.form.SysUserForm;
import com.xht.platform.system.domain.form.UpdatePwdFrom;
import com.xht.platform.system.domain.query.SysUserQuery;
import com.xht.platform.system.domain.response.SysUserDetailResponse;
import com.xht.platform.system.domain.response.SysUserResponse;
import com.xht.platform.system.domain.vo.SysUserVO;
import com.xht.platform.system.entity.SysUserDetailEntity;
import com.xht.platform.system.entity.SysUserEntity;
import com.xht.platform.system.helper.SysUserHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 用户Service实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements ISysUserService {

    private final SysUserDao sysUserDao;

    private final SysUserConverter sysUserConverter;

    private final SysUserDetailDao sysUserDetailDao;

    /**
     * 用户注册
     *
     * @param userForm 用户创建请求对象
     */
    @Override
    public void create(SysUserForm userForm) {
        SysUserDetailForm detail = userForm.getProfile();
        String idCard = detail.getIdCard();
        String userPhone = userForm.getUserPhone();
        UserTypeEnum userType = userForm.getUserType();
        AssertUtils.isTrue(!Objects.equals(UserTypeEnum.ADMIN, userType), "管理员用户不能注册");
        // 校验当前用户信息是否已经注册过
        boolean userPhoneExists = sysUserDao.checkUserPhoneExists(userPhone, null);
        AssertUtils.isTrue(!userPhoneExists, BusinessErrorCode.DATA_EXIST, "该手机号已注册过账号，若为本人操作，可直接登录或联系客服核实！");
        boolean userIdCardExists = sysUserDetailDao.checkUserIdCardExists(idCard, null);
        AssertUtils.isTrue(!userIdCardExists, BusinessErrorCode.DATA_EXIST, "该身份证号已注册过账号，若为本人操作，可直接登录或联系客服核实！");
        // 格式化数据类型
        SysUserEntity sysUser = SysUserHelper.formatUser(userForm);
        String password = PassWordUtils.generatePassword();
        sysUser.setId(IdUtil.getSnowflakeNextId());
        sysUser.setPassWord(PassWordUtils.encodePassword(password));
        sysUser.setPassWordPlainText(password);
        SysUserDetailEntity detailEntity = SysUserHelper.formatUser(detail, sysUser.getId());
        sysUserDetailDao.saveUserInfo(sysUser, detailEntity);
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    @Override
    public void removeByUserId(Long userId) {
        Boolean exists = sysUserDao.exists(SysUserEntity::getId, userId);
        AssertUtils.isTrue(exists, BusinessErrorCode.DATA_NOT_EXIST, "用户不存在");
        sysUserDao.removeById(userId);
        sysUserDetailDao.removeByUserId(userId);
    }

    /**
     * 更新用户信息
     *
     * @param userId   用户 ID
     * @param userForm 用户更新请求对象
     */
    @Override
    public void updateById(Long userId, SysUserForm userForm) {
        SysUserDetailForm detail = userForm.getProfile();
        String idCard = detail.getIdCard();
        String userPhone = userForm.getUserPhone();
        Boolean userExists = sysUserDao.exists(SysUserEntity::getId, userId);
        AssertUtils.isTrue(userExists, BusinessErrorCode.DATA_NOT_EXIST);
        Boolean userInfoExists = sysUserDetailDao.exists(SysUserDetailEntity::getUserId, userId);
        AssertUtils.isTrue(userInfoExists, BusinessErrorCode.DATA_NOT_EXIST);
        UserTypeEnum userType = userForm.getUserType();
        if (!SecurityUtils.isAdmin()) {
            AssertUtils.isTrue(!Objects.equals(UserTypeEnum.ADMIN, userType), "暂无权限修改用户");
        }
        // 校验当前用户信息是否已经注册过
        boolean userPhoneExists = sysUserDao.checkUserPhoneExists(userPhone, userId);
        AssertUtils.isTrue(!userPhoneExists, BusinessErrorCode.DATA_EXIST, "该手机号已注册过账号，若为本人操作，可直接登录或联系客服核实！");
        boolean userIdCardExists = sysUserDetailDao.checkUserIdCardExists(idCard, userId);
        AssertUtils.isTrue(!userIdCardExists, BusinessErrorCode.DATA_EXIST, "该身份证号已注册过账号，若为本人操作，可直接登录或联系客服核实！");
        // 格式化数据类型
        SysUserEntity sysUser = SysUserHelper.formatUser(userForm);
        sysUser.setId(userId);
        SysUserDetailEntity detailEntity = SysUserHelper.formatUser(detail, userId);
        sysUserDetailDao.updateUserInfo(sysUser, detailEntity);
    }

    /**
     * 重置密码
     *
     * @param userId 用户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long userId) {
        SysUserEntity sysUserEntity = sysUserDao.findOptionalById(userId).orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST, "用户不存在"));
        String password = PassWordUtils.generatePassword();
        sysUserDao.updatePassword(userId, PassWordUtils.encodePassword(password), password);
        String userPhone = sysUserEntity.getUserPhone();
        log.info("用户{}密码已重置为`{}`,开始向{}发送短信", userId, password, userPhone);
    }

    /**
     * 修改密码
     *
     * @param form 用户更新请求对象
     */
    @Override
    public void updatePassword(UpdatePwdFrom form) {
        Long userId = SecurityUtils.getUserId();
        SysUserEntity sysUserEntity = sysUserDao.findById(userId);
        if (!Objects.equals(sysUserEntity.getUserStatus(), UserStatusEnum.NORMAL)) {
            throw new BusinessException("用户状态不正确！");
        }
        String oldPassword = form.getOldPassword();
        String newPassword = form.getNewPassword();
        String confirmPassword = form.getConfirmPassword();
        if (!PassWordUtils.matchPassword(oldPassword, sysUserEntity.getPassWord())) {
            throw new BusinessException("旧密码错误");
        }
        if (!StringUtils.equals(newPassword, confirmPassword)) {
            throw new BusinessException("两次密码输入不一致");
        }
        if (PassWordUtils.matchPassword(newPassword, sysUserEntity.getPassWord())) {
            throw new BusinessException("修改密码与原密码相同");
        }
        if (PassWordUtils.matchPassword(confirmPassword, sysUserEntity.getPassWord())) {
            throw new BusinessException("修改密码与原密码相同");
        }
        sysUserDao.updatePassword(userId, PassWordUtils.encodePassword(newPassword), null);
    }

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     */
    @Override
    public void updateStatus(Long userId, UserStatusEnum status) {
        Boolean exists = sysUserDao.exists(SysUserEntity::getId, userId);
        AssertUtils.isTrue(!exists, BusinessErrorCode.DATA_NOT_EXIST, "用户不存在");
        sysUserDao.updateStatus(userId, status);
    }


    /**
     * 根据ID查找用户
     *
     * @param userId 用户ID
     * @return 找到的用户对象，不存在时返回null
     */
    @Override
    public SysUserVO findByUserId(Long userId) {
        AssertUtils.notNull(userId, "用户ID不能为空");
        SysUserVO sysUserVO = sysUserDao.findInfoByUserId(userId);
        AssertUtils.notNull(sysUserVO, "查询不到用户信息!");
        if (Objects.isNull(sysUserVO.getProfile())) {
            SysUserDetailResponse sysUserDetailResponse = new SysUserDetailResponse();
            sysUserDetailResponse.setUserId(sysUserVO.getId());
            sysUserVO.setProfile(sysUserDetailResponse);
        }
        return sysUserVO;
    }

    /**
     * 根据查询条件分页查找用户
     *
     * @param query 用户查询请求对象
     * @return 用户对象分页结果
     */
    @Override
    public PageResponse<SysUserResponse> findPageList(SysUserQuery query) {
        Page<SysUserEntity> sysUserEntityPage = sysUserDao.findPageList(PageTool.getPage(query), query);
        return sysUserConverter.toResponse(sysUserEntityPage);
    }


}
