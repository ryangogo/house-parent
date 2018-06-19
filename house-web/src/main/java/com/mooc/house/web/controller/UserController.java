package com.mooc.house.web.controller;

import com.mooc.house.biz.service.UserService;
import com.mooc.house.common.model.User;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpClient httpClient;

    @RequestMapping("getUsers")
    @ResponseBody
    public List<User> getAllUser() {
        return userService.selectAllUser();
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("propertyDetail")
    public String propertydetail() {
        return "property-detail";
    }

    @GetMapping("testHttpclient")
    @ResponseBody
    public String testHttpclient() throws IOException {
        String result = EntityUtils.toString(httpClient.execute(new HttpGet("http://www.baidu.com")).
                getEntity());
        return result;
    }


}
