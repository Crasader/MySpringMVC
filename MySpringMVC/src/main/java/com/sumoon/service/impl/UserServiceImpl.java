package com.sumoon.service.impl;


import com.sumoon.annotation.Service;
import com.sumoon.service.UserService;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String getName() {
        System.out.println("调用userservice成功");
        return "zhangsan";
    }

}