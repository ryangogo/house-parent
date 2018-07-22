package com.mooc.house.web.controller;

import com.github.pagehelper.PageInfo;
import com.mooc.house.biz.service.HouseService;
import com.mooc.house.common.constants.CommonConstants;
import com.mooc.house.common.model.House;
import com.mooc.house.common.model.User;
import com.mooc.house.common.result.ServerResponse;
import com.mooc.house.common.vo.HouseVO;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/6/27.
 */
@Controller
@RequestMapping("/house/")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @GetMapping("list")
    public String list() {

        return "houses/list";
    }

    @PostMapping("list")
    @ResponseBody
    public ServerResponse<PageInfo> list(String name, String type, String sorting,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "2") int pageSize) {
        return houseService.listHouse(pageNum, pageSize, name, type, sorting);
    }


    @GetMapping("add")
    public String add(Model model) {
        HouseVO houseVO = houseService.getCitiesAndCommunities();
        val cities = houseVO.getCities();
        val communities = houseVO.getCommunities();
        model.addAttribute("cities", cities);
        model.addAttribute("communities", communities);
        return "houses/add";
    }

    @PostMapping("add")
    public String add(HttpSession session, House house, MultipartFile[] houseFiles, MultipartFile[] floorPlanFiles, String[] featureList) {
        User user = (User) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
        houseService.add(house, houseFiles, floorPlanFiles, featureList, user);
        return "houses/add";
    }

    @GetMapping("detail")
    public String detail(Model model, String id) {
        House house = houseService.getHouseDetail(id);
        User agencyUser = houseService.getUserByHouseId(house.getId());
        model.addAttribute("house", house);
        model.addAttribute("agencyUser", agencyUser);
        return "houses/detail";
    }

    @PostMapping("sendEmail")
    @ResponseBody
    public ServerResponse sendEmail(Integer houseId, Integer agencyId, String msg, HttpSession session) {
        User user = (User) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
        houseService.sendEmailToAgent(houseId, agencyId, msg, user);
        return ServerResponse.createBySuccessMessage("发送邮件成功");
    }

    @PostMapping("comment")
    @ResponseBody
    public ServerResponse comment(Integer houseId, Integer agentId, String msg, HttpSession session) {
        User user = (User) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
        return houseService.addComment(houseId, agentId, msg, user);
    }

    @GetMapping("commons")
    @ResponseBody
    public ServerResponse commons(Integer houseId) {
        return houseService.getCommons(houseId);
    }


}
