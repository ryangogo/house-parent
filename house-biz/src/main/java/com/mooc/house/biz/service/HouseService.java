package com.mooc.house.biz.service;

import com.mooc.house.biz.mapper.HouseMapper;
import com.mooc.house.common.model.House;
import com.mooc.house.common.vo.HouseVO;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2018/6/28.
 */
@Service
public class HouseService {

    @Autowired
    private HouseMapper houseMapper;

    public HouseVO getCitiesAndCommunities() {
        val cities = houseMapper.selectAllCity();
        val communities = houseMapper.selectAllCommunity();
        HouseVO houseVO = new HouseVO();
        houseVO.setCities(cities);
        houseVO.setCommunities(communities);
        return houseVO;
    }

    public void add(House house, MultipartFile[] houseFiles, MultipartFile[] floorPlanFiles, String[] featureList) {


    }


}
