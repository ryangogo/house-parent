package com.mooc.house.biz.mapper;

import com.mooc.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> selectAll();

    @Select("select count(1) from user where name = #{username}")
    int CheckUserName(@Param("username") String username);

    @Select("select count(1) from user where email = #{email}")
    int CheckEmail(@Param("email") String email);

    int insert(User user);

}
