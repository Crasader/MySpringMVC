package com.sumoon.controller;

import com.sumoon.annotation.Autowired;
import com.sumoon.annotation.Controller;
import com.sumoon.annotation.RequestMapping;
import com.sumoon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户控制器
 */
@Controller
@RequestMapping("/uc")
public class UserController {

    /**
     * logger
     */
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * get current username
     * @return
     */
    @RequestMapping("/getName")
    public String getName() {
        LOGGER.info("get username: {}", userService.getName());
        return userService.getName();
    }

}