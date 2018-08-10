package com.mooc.house.biz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mooc.house.biz.mapper.HouseMapper;
import com.mooc.house.biz.mapper.UserMapper;
import com.mooc.house.common.constants.CommonConstants;
import com.mooc.house.common.constants.HouseUserType;
import com.mooc.house.common.model.House;
import com.mooc.house.common.model.HouseMsg;
import com.mooc.house.common.model.HouseUser;
import com.mooc.house.common.model.User;
import com.mooc.house.common.result.ServerResponse;
import com.mooc.house.common.utils.DateUtil;
import com.mooc.house.common.utils.QiNiuCDNOperator;
import com.mooc.house.common.vo.HouseVO;
import com.qiniu.storage.model.DefaultPutRet;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private MailService mailService;

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
        house.setRating((double) 0);
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

    public House getHouseDetail(String id) {
        House house = houseMapper.selectById(id);
        String properties = house.getProperties();
        String[] propertyArray = properties.split("\\|");
        for (String property : propertyArray) {
            house.getPropertyList().add(property);
        }
        for (String image : house.getImages().split("\\|")) {
            house.getImageList().add(CommonConstants.DEFAULT_QINIU_URL + image);
        }
        for (String floorPlan : house.getFloorPlan().split("\\|")) {
            house.getFloorPlanList().add(CommonConstants.DEFAULT_QINIU_URL + floorPlan);
        }
        return house;
    }

    public User getUserByHouseId(long id) {
        User user = houseMapper.selectUserByHouseId(id);
        user.setAvatar(CommonConstants.DEFAULT_QINIU_URL + user.getAvatar());
        return user;
    }

    public void sendEmailToAgent(Integer houseId, Integer agencyId, String msg, User loginUser) {
        House house = houseMapper.selectById(String.valueOf(houseId));
        User agent = userMapper.selectById(agencyId);
        mailService.sendMailToAgent(house, agent, msg, loginUser);
    }

    public ServerResponse addComment(Integer houseId, Integer agentId, String msg, User user) {
        HouseMsg houseMsg = new HouseMsg();
        Date nowDate = DateUtil.getNowDate();
        houseMsg.setCreateTime(nowDate);
        houseMsg.setAgentId(agentId);
        houseMsg.setHouseId(houseId);
        houseMsg.setMsg(msg);
        houseMsg.setUserAvatar(user.getAvatar());
        houseMsg.setUserName(user.getName());
        houseMsg.setUserId(user.getId());
        int flag = houseMapper.insertHouseMsg(houseMsg);
        if (flag == 1) {

            houseMsg.setCreateTimeStr(DateUtil.dateToStrYMD(nowDate));
            return ServerResponse.createBySuccess(houseMsg);
        } else {
            return ServerResponse.createBySuccessMessage("评论失败");
        }
    }

    public ServerResponse getCommons(Integer houseId) {
        List<HouseMsg> list = houseMapper.selectCommonsByHouseId(houseId);
        for (HouseMsg hm : list) {
            hm.setCreateTimeStr(DateUtil.dateToStrYMD(hm.getCreateTime()));
        }
        return ServerResponse.createBySuccess(list);
    }

    public List<House> findHotByIds(List<Long> ids) {
        return houseMapper.selectByHotIds(ids);
    }

    public void updateRating(String id, Double rating) {
        Jedis jedis = new Jedis("127.0.0.1");


    }
}
