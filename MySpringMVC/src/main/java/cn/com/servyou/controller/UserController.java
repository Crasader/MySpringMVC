package cn.com.servyou.controller;

import cn.com.servyou.annotation.Controller;
import cn.com.servyou.annotation.Qualifier;
import cn.com.servyou.annotation.RequestMapping;
import cn.com.servyou.service.UserService;

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