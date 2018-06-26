package com.mooc.house.biz.mapper;

import com.mooc.house.common.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> selectAll();

    @Select("select count(1) from user where name = #{username}")
    int CheckUserName(@Param("username") String username);

    @Select("select count(1) from user where email = #{email}")
    int CheckEmail(@Param("email") String email);

    @Select("select enable from user where email = #{email}")
    int findEnableByEmail(@Param("email") String email);

    int insert(User user);

    @Delete("delete from user where email=#{email}")
    void delete(@Param("email") String email);

    @Update("UPDATE user SET enable = #{enable} WHERE email = #{email}")
    int setEnable(@Param("enable") int enable, @Param("email") String email);
}
