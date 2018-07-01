package com.mooc.house.web.controller;

import com.mooc.house.biz.service.UserService;
import com.mooc.house.common.constants.CommonConstants;
import com.mooc.house.common.model.User;
import com.mooc.house.common.result.ResultMsg;
import com.mooc.house.common.result.ServerResponse;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

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
     * 注册：1注册验证 2发送邮件 3验证失败重定向到注册页面
     *
     * @param account
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public ServerResponse accountsRegister(User account, MultipartFile avatarFile) {
        return userService.addCount(account, avatarFile);
    }

    @GetMapping("registerSubmit")
    public String registerSubmit(String email, Model model) {
        model.addAttribute("email", email);
        return "accounts/registerSubmit";
    }

    @RequestMapping("verify")
    public String verify(String key, Model model) {
        boolean result = userService.enable(key);
        if (result) {
            model.addAttribute("msg", "激活成功");
            return "index";
        } else {
            model.addAttribute("msg", "key不存在");
            return "accounts/register";
        }

    }

    //-----------------------------------------------------登录-----------------------------------------------------

    @GetMapping("login")
    public String login() {
        return "accounts/login";
    }

    @PostMapping("login")
    public String login(String email, String password, Model model, HttpSession session) {
        User user = userService.auth(email, password);
        if (user != null) {
            session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
            session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE, user);
            model.addAttribute(ResultMsg.successMsgKey, "登录成功");
            return "homepage/index";
        } else {
            model.addAttribute(ResultMsg.errorMsgKey, "登录失败");
            model.addAttribute("email", email);
            model.addAttribute("password", password);
            return "accounts/login";
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute(CommonConstants.USER_ATTRIBUTE);
        session.removeAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE);
        return "redirect:/account/login";
    }

    @GetMapping("retrieve")
    public String retrieve(String key, String email, Model model) {
        if (CommonConstants.FORGET_PWD_CODE.equals(key) && StringUtils.isBlank(email)) {//跳转到邮箱获取页面
            return "accounts/getEmail";
        } else {
            model.addAttribute("email", email);
            model.addAttribute("key", key);
            return "accounts/retrieve";
        }
    }

    @PostMapping("retrieve")
    public String retrieve(String email, String key, String password, Model model) {
        //获取email并且发送邮件到邮箱
        if (StringUtils.isBlank(key) && StringUtils.isBlank(password)) {
            if (userService.checkEmail(email)) {
                userService.retrieve(email);
                model.addAttribute("email", email);
                return "accounts/passwordRetrieve";
            } else {
                model.addAttribute(ResultMsg.errorMsgKey, "邮箱不存在");
                return "accounts/getEmail";
            }
        }
        val returnMap = userService.revise(email, key, password);
        if ((boolean) returnMap.get("status")) {
            return "accounts/login";
        } else {
            model.addAttribute(ResultMsg.errorMsgKey, (String) returnMap.get("message"));
            model.addAttribute("email", email);
            model.addAttribute("key", key);
            return "accounts/retrieve";
        }
    }

    //-----------------------------------------------------个人信息-----------------------------------------------------

    @GetMapping("message")
    public String message() {
        return "accounts/message";
    }

    @GetMapping("changePassword")
    public String changePassword() {//防止用户刷新报错
        return "accounts/message";
    }

    @GetMapping("changeMessage")
    public String changeMessage() {//防止用户刷新报错
        return "accounts/message";
    }

    @PostMapping("changePassword")
    public String changePassword(HttpSession session, String password, String newPassword, String confirmPassword, Model model) {
        User user = (User) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
        user.setPasswd(password);
        user.setNewPassword(newPassword);
        user.setConfirmPasswd(confirmPassword);
        HashMap<String, Object> resultMap = userService.changePassword(user);
        if ((boolean) resultMap.get("status")) {
            model.addAttribute(ResultMsg.successMsgKey, (String) resultMap.get("message"));
        } else {
            model.addAttribute(ResultMsg.errorMsgKey, (String) resultMap.get("message"));
        }
        return "accounts/message";
    }

    @PostMapping("changeMessage")
    public String changeMessage(HttpSession session, String name,
                                String phone, String email, String aboutme, Model model) {
        User user = (User) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
        HashMap<String, Object> resultMap = userService.modifyUser(session, user, name, phone, email, aboutme);
        if ((boolean) resultMap.get("status")) {
            model.addAttribute(ResultMsg.successMsgKey, (String) resultMap.get("message"));
        } else {
            model.addAttribute(ResultMsg.errorMsgKey, (String) resultMap.get("message"));
        }
        return "accounts/message";
    }

}
