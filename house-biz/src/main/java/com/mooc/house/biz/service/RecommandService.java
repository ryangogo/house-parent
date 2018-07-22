package com.mooc.house.biz.service;

import com.mooc.house.biz.mapper.HouseMapper;
import com.mooc.house.common.constants.CommonConstants;
import com.mooc.house.common.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Ryan on 2018/7/22.
 */
@Service
public class RecommandService {

    private static final String HOT_HOUSE_KEY = "hot_house";

    @Autowired
    private HouseService houseService;

    @Autowired
    private HouseMapper houseMapper;

    public void increase(Long id) {
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.zincrby(HOT_HOUSE_KEY, 1.0D, id + "");
        jedis.zremrangeByRank(HOT_HOUSE_KEY, 10, -1);//排序之后删除10位意外的元素列表 -1代表结尾从第10位开始
        jedis.close();
    }

    public List<Long> getHot() {
        Jedis jedis = new Jedis("127.0.0.1");
        Set<String> idSet = jedis.zrange(HOT_HOUSE_KEY, 0, 3);
        jedis.close();
        List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());//将idSet中的string类型转换为long类型
        return ids;
    }

    public ArrayList<House> getHotHouse() {
        List<Long> ids = getHot();
        //List<House> houseList = houseService.findHotByIds(ids);
        ArrayList<House> houseList = new ArrayList<>();
        for (Long id : ids) {
            House house = houseMapper.selectById(id.toString());
            String[] images = house.getImages().split("\\|");
            for (String image : images) {
                house.getImageList().add(CommonConstants.DEFAULT_QINIU_URL + image);
            }
            String[] floorPlans = house.getFloorPlan().split("\\|");
            for (String floorPlan : floorPlans) {
                house.getFloorPlanList().add(CommonConstants.DEFAULT_QINIU_URL + floorPlan);
            }
            houseList.add(house);
        }
        return houseList;
    }

}
