package com.mooc.house.biz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mooc.house.biz.mapper.HouseMapper;
import com.mooc.house.biz.mapper.UserMapper;
import com.mooc.house.common.constants.CommonConstants;
import com.mooc.house.common.constants.HouseUserType;
import com.mooc.house.common.model.House;
import com.mooc.house.common.model.HouseUser;
import com.mooc.house.common.model.User;
import com.mooc.house.common.result.ServerResponse;
import com.mooc.house.common.utils.QiNiuCDNOperator;
import com.mooc.house.common.vo.HouseVO;
import com.qiniu.storage.model.DefaultPutRet;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2018/6/28.
 */
@Service
public class HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private UserMapper userMapper;

    public HouseVO getCitiesAndCommunities() {
        val cities = houseMapper.selectAllCity();
        val communities = houseMapper.selectAllCommunity();
        HouseVO houseVO = new HouseVO();
        houseVO.setCities(cities);
        houseVO.setCommunities(communities);
        return houseVO;
    }

    @Transactional
    public void add(House house, MultipartFile[] houseFiles,
                    MultipartFile[] floorPlanFiles, String[] featureList, User user) {
        //添加户型图
        if (floorPlanFiles != null || floorPlanFiles.length != 0) {
            String floorPlans = getFileName(floorPlanFiles);
            house.setFloorPlan(floorPlans);
        }
        //添加图片
        if (houseFiles != null || houseFiles.length != 0) {
            String images = getFileName(houseFiles);
            house.setImages(images);
        }
        //添加上架状态
        house.setState(CommonConstants.HOUSE_STATE_UP);
        int flag = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (String feature : featureList) {
            flag++;
            if (flag == featureList.length) {
                stringBuilder.append(feature);
            } else {
                stringBuilder.append(feature).append("|");
            }
        }
        String properties = stringBuilder.toString();
        house.setProperties(properties);
        //TODO 这个评级是什么鬼 先添加一个默认的0对付一下吧
        house.setRating((double) 0);
        //TODO 这个标签又是个什么鬼 先添加一个默认的“default”对付一下吧
        house.setTags("default");
        //各种数据添加完毕 准备上传房产
        houseMapper.insert(house);
        //添加用户和房产的连接关系
        HouseUser houseUser = new HouseUser();
        houseUser.setHouseId(house.getId());
        houseUser.setType(HouseUserType.SALE.value);
        houseUser.setUserId(user.getId());
        houseMapper.insertHouseUser(houseUser);

    }

    private String getFileName(MultipartFile[] files) {
        StringBuilder stringBuilder = new StringBuilder();
        int flag = 0;
        for (MultipartFile file : files) {
            flag++;
            UUID uuid = UUID.randomUUID();
            String str = uuid.toString();
            String uuidStr = str.replace("-", "");
            FileInputStream inputStream = null;
            try {
                inputStream = (FileInputStream) file.getInputStream();
                DefaultPutRet db = QiNiuCDNOperator.fileUpload(inputStream, uuidStr);
                if (flag == files.length) {
                    stringBuilder.append(db.key);
                } else {
                    stringBuilder.append(db.key).append("|");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    public ServerResponse<PageInfo> listHouse(int pageNum, int pageSize,
                                              String name, String type, String sorting) {
        PageHelper.startPage(pageNum, pageSize);
        val houseList = houseMapper.listPageHouse(name, type, sorting);
        for (House house : houseList) {
            String[] images = house.getImages().split("\\|");
            for (String image : images) {
                house.getImageList().add(CommonConstants.DEFAULT_QINIU_URL + image);
            }
            String[] floorPlans = house.getFloorPlan().split("\\|");
            for (String floorPlan : floorPlans) {
                house.getFloorPlanList().add(CommonConstants.DEFAULT_QINIU_URL + floorPlan);
            }
        }
        PageInfo pageInfo = new PageInfo<>(houseList, pageSize);
        return ServerResponse.createBySuccess(pageInfo);
    }


}
