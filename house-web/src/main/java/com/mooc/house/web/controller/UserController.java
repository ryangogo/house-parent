package com.mooc.house.web.controller;

import com.mooc.house.biz.service.UserService;
import com.mooc.house.common.model.User;
import com.mooc.house.common.result.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/account/")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到注册页
     *
     * @return
     */
    @GetMapping("register")
    public String accountsRegister() {
        return "accounts/register";
    }

    /**
     * 注册提交：1注册验证 2发送邮件 3验证失败重定向到注册页面
     *
     * @param account
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public ServerResponse accountsRegister(User account, MultipartFile avatarFile) {
        return userService.userRegister(account,avatarFile);
    }



}
