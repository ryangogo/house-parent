package com.mooc.house.web.controller;

import com.mooc.house.biz.service.RecommandService;
import com.mooc.house.common.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by Ryan on 2018/7/29.
 */
@Controller
public class HomePageController {

    @Autowired
    private RecommandService recommandService;

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("index")
    public String index(Model model) {
        List<House> houseList = recommandService.getLastest();//获取最新房源
        List<House> hotHouseList = recommandService.getHotHouse();
        model.addAttribute("houses", houseList);
        model.addAttribute("hotHouses", hotHouseList);
        return "homepage/index";
    }

}
