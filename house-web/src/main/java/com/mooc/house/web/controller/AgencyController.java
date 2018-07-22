package com.mooc.house.web.controller;

import com.github.pagehelper.PageInfo;
import com.mooc.house.biz.service.AgencyService;
import com.mooc.house.common.result.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ryan on 2018/7/10.
 */

@Controller
@RequestMapping("/agency/")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @GetMapping("agencyList")
    public String agencyList() {
        return "agency/agencyList";
    }

    @PostMapping("agencyList")
    public String agencyList(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "2") int pageSize) {
        return "agency/agencyList";
    }

    @GetMapping("agentList")
    public String agentList() {
        return "agency/agentList";
    }

    @PostMapping("agentList")
    @ResponseBody
    public ServerResponse<PageInfo> agentList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {
        return agencyService.getAllAgent(pageNum, pageSize);
    }

    @GetMapping("agentDetail")
    public String agentDetail() {
        return "agency/agencyDetail";
    }


}
