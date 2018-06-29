package com.mooc.house.web.controller;

import com.mooc.house.biz.service.HouseService;
import com.mooc.house.common.model.House;
import com.mooc.house.common.vo.HouseVO;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2018/6/27.
 */
@Controller
@RequestMapping("/house/")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @RequestMapping("list")
    public String list() {
        return "houses/list";
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
    public String add(House house, MultipartFile[] houseFiles, MultipartFile[] floorPlanFiles, String[] featureList) {
        houseService.add(house, houseFiles, floorPlanFiles, featureList);
        return "houses/add";
    }
}
