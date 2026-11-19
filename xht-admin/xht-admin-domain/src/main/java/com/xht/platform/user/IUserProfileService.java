package com.xht.platform.user;

import com.xht.framework.utils.tree.INode;
import com.xht.platform.login.domain.response.UserLoginLogResponse;
import com.xht.platform.system.domain.vo.SysUserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 描述： 用户信息接口
 *
 * @author xht
 **/
public interface IUserProfileService {

    /**
     * 上传头像
     *
     * @param avatar 头像文件
     */
    String uploadAvatar(MultipartFile avatar);

    /**
     * 获取当前登录的用户信息
     *
     * @return 用户信息
     */
    SysUserVO getUserProfileInfo();

    /**
     * 获取当前登录用户的路由信息
     *
     * @return 路由信息
     */
    List<INode<Long>> getRouters();

    /**
     * 获取当前登录用户登录日志
     *
     * @return 登录日志信息
     */
    List<UserLoginLogResponse> getLoginLog();

}
