package com.xht.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import  com.xht.demo.domain.Users;
import com.xht.demo.service.UsersService;
import  com.xht.demo.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author xht
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

}




