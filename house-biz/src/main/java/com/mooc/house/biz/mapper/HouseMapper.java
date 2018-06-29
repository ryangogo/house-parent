package com.mooc.house.biz.mapper;

import com.mooc.house.common.model.City;
import com.mooc.house.common.model.Community;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2018/6/28.
 */
@Mapper
public interface HouseMapper {

    @Select("select city_code as id,city_name as cityName from community group by city_name")
    List<City> selectAllCity();

    @Select("select id,name from community group by name")
    List<Community> selectAllCommunity();
}
