package com.sumoon.controller;

import com.sumoon.annotation.Controller;
import com.sumoon.annotation.Qualifier;
import com.sumoon.annotation.RequestMapping;
import com.sumoon.service.UserService;

/**
 * 用户控制器
 */
@Controller("userController")
@RequestMapping("/uc")
public class UserController {

    @Qualifier("userService")
    private UserService userService;

    @RequestMapping("/getName")
    public String getName() {
        System.out.println("username: " + userService.getName());
        return userService.getName();
    }

}