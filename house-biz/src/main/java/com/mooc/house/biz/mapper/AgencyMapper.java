package com.mooc.house.biz.mapper;

import com.mooc.house.common.model.Agency;
import com.mooc.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Ryan on 2018/7/10.
 */


@Mapper
public interface AgencyMapper {

    List<Agency> listPageHouse();

    @Select("select * from user where enable = 1 and type = 2")
    List<User> listPageAgent();
}
