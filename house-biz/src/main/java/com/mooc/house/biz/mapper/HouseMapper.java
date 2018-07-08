package com.mooc.house.biz.mapper;

import com.mooc.house.common.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    int insert(House house);

    int insertHouseUser(HouseUser houseUser);

    List<House> listPageHouse(@Param("name") String name, @Param("type") String type, @Param("sorting") String sorting);

    @Select("select * from house where id = #{id}")
    House selectById(@Param("id") String id);

    User selectUserByHouseId(@Param("houseId") Long houseId);
}
