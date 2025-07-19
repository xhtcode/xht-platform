package com.xht.system.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.code.UserErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.secret.MD5Utils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.system.modules.dept.domain.entity.SysDeptEntity;
import com.xht.system.modules.dept.domain.entity.SysDeptPostEntity;
import com.xht.system.modules.dept.domain.vo.SysDeptPostVo;
import com.xht.system.modules.dept.dao.SysDeptDao;
import com.xht.system.modules.dept.dao.SysDeptPostDao;
import com.xht.system.event.SysUserDeptUpdateEvent;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import com.xht.system.modules.user.domain.entity.SysUserDeptEntity;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.entity.SysUserProfilesEntity;
import com.xht.system.modules.user.domain.request.UpdatePwdRequest;
import com.xht.system.modules.user.domain.request.UserFormRequest;
import com.xht.system.modules.user.domain.request.UserProfilesFormRequest;
import com.xht.system.modules.user.domain.request.UserQueryRequest;
import com.xht.system.modules.user.domain.vo.SysUserVO;
import com.xht.system.modules.user.dao.SysUserDeptDao;
import com.xht.system.modules.user.dao.SysUserDao;
import com.xht.system.modules.user.service.IUserService;
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
public class UserServiceImpl implements IUserService {

    private final SysUserDao sysUserDao;

    private final SysUserDeptDao sysUserDeptDao;

    private final SysDeptDao sysDeptDao;

    private final SysDeptPostDao sysDeptPostDao;

    /**
     * 获取用户信息实体
     *
     * @param formRequest 用户创建请求对象
     * @return 用户信息实体
     */
    private static SysUserProfilesEntity getSysUserProfilesEntity(UserFormRequest formRequest) {
        SysUserProfilesEntity sysUserProfilesEntity = new SysUserProfilesEntity();
        UserProfilesFormRequest profile = formRequest.getProfile();
        sysUserProfilesEntity.setRealName(profile.getRealName());
        sysUserProfilesEntity.setIdCardNumber(profile.getIdCardNumber());
        sysUserProfilesEntity.setGender(profile.getGender());
        sysUserProfilesEntity.setBirthDate(profile.getBirthDate());
        sysUserProfilesEntity.setAge(profile.getAge());
        sysUserProfilesEntity.setAddress(profile.getAddress());
        sysUserProfilesEntity.setPostalCode(profile.getPostalCode());
        return sysUserProfilesEntity;
    }

    private static SysUserDeptEntity getSysUserDeptEntity(UserFormRequest formRequest) {
        SysUserDeptEntity sysUserDeptEntity = new SysUserDeptEntity();
        sysUserDeptEntity.setDeptId(formRequest.getDeptId());
        sysUserDeptEntity.setPostId(formRequest.getPostId());
        return sysUserDeptEntity;
    }

    /**
     * 用户注册
     *
     * @param formRequest 用户创建请求对象
     * @return 操作成功返回true，否则返回false
     */
    @Override
    public Boolean create(UserFormRequest formRequest) {
        Boolean idCardNumberExists = sysUserDao.existsIdCardNumber(formRequest.getProfile().getIdCardNumber());
        ThrowUtils.throwIf(idCardNumberExists, BusinessErrorCode.DATA_EXIST, "身份证号码已存在");
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserName("admin");//todo 后期改为账号序列
        sysUserEntity.setPassWord("123456");
        sysUserEntity.setDeptId(formRequest.getDeptId());
        sysUserEntity.setNickName(formRequest.getNickName());
        sysUserEntity.setAvatarUrl("https://xht.com");
        sysUserEntity.setUserStatus(UserStatusEnums.NORMAL);
        Boolean deptExists = sysDeptDao.exists(SysDeptEntity::getId, formRequest.getDeptId());
        ThrowUtils.throwIf(!deptExists, BusinessErrorCode.DATA_EXIST, "部门不存在");
        SysDeptPostEntity postExists = sysDeptPostDao.findPostByDeptIdAndPostId(formRequest.getDeptId(), formRequest.getPostId());
        ThrowUtils.throwIf(Objects.isNull(postExists), BusinessErrorCode.DATA_EXIST, "部门暂无此岗位");
        Boolean postLimit = sysDeptPostDao.validatePostLimit(formRequest.getPostId());
        ThrowUtils.throwIf(postLimit, BusinessErrorCode.DATA_EXIST, "岗位人数已满");
        return sysUserDao.saveUserInfo(sysUserEntity, getSysUserProfilesEntity(formRequest), formRequest.getPostId());
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
        return sysUserDao.removeByIds(ids);
    }

    /**
     * 更新用户信息
     *
     * @param formRequest 用户更新请求对象
     * @return 操作成功返回true，否则返回false
     */
    @Override
    public Boolean update(UserFormRequest formRequest) {
        Long userId = formRequest.getId();
        Boolean exists = sysUserDao.exists(SysUserEntity::getId, userId);
        ThrowUtils.throwIf(!exists, BusinessErrorCode.DATA_NOT_EXIST, "用户不存在");
        Boolean deptExists = sysDeptDao.exists(SysDeptEntity::getId, formRequest.getDeptId());
        ThrowUtils.throwIf(!deptExists, BusinessErrorCode.DATA_EXIST, "部门不存在");
        SysDeptPostEntity postExists = sysDeptPostDao.findPostByDeptIdAndPostId(formRequest.getDeptId(), formRequest.getPostId());
        ThrowUtils.throwIf(Objects.isNull(postExists), BusinessErrorCode.DATA_EXIST, "部门暂无此岗位");
        SysUserDeptEntity oldUserDeptEntity = sysUserDeptDao.findOneByUserId(userId);
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(userId);
        sysUserEntity.setNickName(formRequest.getNickName());
        sysUserEntity.setDeptId(formRequest.getDeptId());
        sysUserEntity.setUserStatus(formRequest.getUserStatus());
        SysUserProfilesEntity sysUserProfilesEntity = getSysUserProfilesEntity(formRequest);
        sysUserProfilesEntity.setUserId(userId);
        SysUserDeptEntity sysUserDeptEntity = sysUserDeptDao.getOneOpt(SysUserDeptEntity::getUserId, userId).orElseGet(SysUserDeptEntity::new);
        sysUserDeptEntity.setUserId(userId);
        sysUserDeptEntity.setDeptId(formRequest.getDeptId());
        sysUserDeptEntity.setPostId(formRequest.getPostId());
        Boolean postLimit = sysDeptPostDao.validatePostLimit(formRequest.getPostId());
        ThrowUtils.throwIf(postLimit, BusinessErrorCode.DATA_EXIST, "岗位人数已满");
        // todo 后期补充用户信息变更记录
        SysUserDeptUpdateEvent userDeptEntity = new SysUserDeptUpdateEvent(
                userId,
                Objects.isNull(oldUserDeptEntity) ? null : oldUserDeptEntity.getDeptId(), Objects.isNull(oldUserDeptEntity) ? null : oldUserDeptEntity.getPostId(),
                formRequest.getDeptId(), formRequest.getPostId()
        );
        return sysUserDao.updateUserInfo(sysUserEntity, sysUserProfilesEntity, userDeptEntity);
    }

    /**
     * 根据ID查找用户
     *
     * @param id 用户ID
     * @return 找到的用户对象，不存在时返回null
     */
    @Override
    public SysUserVO findById(Long id) {
        SysUserVO sysUserVO = sysUserDao.findInfoByUserId(id);
        ThrowUtils.throwIf(Objects.isNull(sysUserVO), UserErrorCode.DATA_NOT_EXIST, "用户不存在");
        SysDeptPostVo deptPostVo = sysUserDeptDao.getDeptPostByUserId(id);
        if (Objects.nonNull(deptPostVo)) {
            sysUserVO.setPostId(deptPostVo.getPostId());
            sysUserVO.setPostCode(deptPostVo.getPostCode());
            sysUserVO.setPostName(deptPostVo.getPostName());
            sysUserVO.setPostStatus(deptPostVo.getPostStatus());
            sysUserVO.setDeptParentId(deptPostVo.getParentId());
            sysUserVO.setDeptCode(deptPostVo.getDeptCode());
            sysUserVO.setDeptName(deptPostVo.getDeptName());
            sysUserVO.setDeptStatus(deptPostVo.getDeptStatus());
        }
        return sysUserVO;
    }

    /**
     * 根据查询条件分页查找用户
     *
     * @param queryRequest 用户查询请求对象
     * @return 用户对象分页结果
     */
    @Override
    public PageResponse<SysUserVO> findPage(UserQueryRequest queryRequest) {
        Page<SysUserVO> page = sysUserDao.findPage(PageTool.getPage(queryRequest), queryRequest);
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
        SysUserEntity sysUserEntity = sysUserDao.getOptById(userId).orElseThrow(() -> new BusinessException(UserErrorCode.DATA_NOT_EXIST, "用户不存在"));
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
}
