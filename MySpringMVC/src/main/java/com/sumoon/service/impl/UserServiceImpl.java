package com.sumoon.service.impl;


import com.sumoon.annotation.Service;
import com.sumoon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * logger
     */
    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String getName() {
        LOGGER.info("获取用户名：{}", "zhangsan");
        return "zhangsan";
    }

}