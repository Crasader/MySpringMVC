package cn.com.servyou.service.impl;

import cn.com.servyou.annotation.Service;
import cn.com.servyou.service.UserService;

/**
 * 用户服务实现类
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Override
    public String getName() {
        System.out.println("调用userservice成功");
        return "zhangsan";
    }

}