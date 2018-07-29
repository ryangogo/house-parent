package com.mooc.house.web.controller;

import com.mooc.house.biz.service.UserService;
import com.mooc.house.common.model.User;
import lombok.val;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/25.
 */
/*@Controller*/
public class TempController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpClient httpClient;

    @RequestMapping("getUsers")
    @ResponseBody
    public List<User> getAllUser() {
        val listUser = userService.selectAllUser();
        for (User user : listUser) {
            Date createTime = user.getCreateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(sdf.format(createTime));
        }
        return listUser;
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("index")
    public String index() {
        return "homepage/index";
    }

    @GetMapping("index2")
    public String index2() {
        return "index-slider-search-box";
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

    @GetMapping("testError")
    public String testError() throws Exception {
        throw new Exception("抛出异常测试");
    }
}
