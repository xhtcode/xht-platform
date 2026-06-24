package com.xht.auth.authentication.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.auth.authentication.dao.IOauth2ThirdAccountDao;
import com.xht.auth.authentication.dao.mapper.Oauth2ThirdAccountMapper;
import com.xht.auth.authentication.entity.Oauth2ThirdAccountEntity;
import com.xht.framework.common.enums.UserStatusEnums;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.framework.security.utils.PassWordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述： 第三方账号DAO实现类
 *
 * @author xht
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class Oauth2ThirdAccountDaoMapper extends MapperRepositoryImpl<Oauth2ThirdAccountMapper, Oauth2ThirdAccountEntity> implements IOauth2ThirdAccountDao {

    /**
     * 根据openid查询
     *
     * @param openid 第三方账号的openid
     * @return 第三方账号信息
     */
    @Override
    public Oauth2ThirdAccountEntity findByOpenid(String openid) {

        return null;
    }

    /**
     * 注册用户
     *
     * @param dbAccountEntity 第三方账号信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerUser(Oauth2ThirdAccountEntity dbAccountEntity) {
        dbAccountEntity.setPassWordPlainText(PassWordUtils.generatePasswordSalt());
        dbAccountEntity.setUserStatus(UserStatusEnums.UNACTIVATED);
        save(dbAccountEntity);
        baseMapper.insertUser(dbAccountEntity);
        baseMapper.insertUserInfo(dbAccountEntity);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<Oauth2ThirdAccountEntity, ?> getFieldId() {
        return Oauth2ThirdAccountEntity::getId;
    }

}
