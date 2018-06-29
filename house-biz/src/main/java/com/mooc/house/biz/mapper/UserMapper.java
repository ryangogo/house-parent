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

    @Update("UPDATE user SET passwd = #{password} WHERE email = #{email} and enable = 1")
    int modifyPasswordByEmail(@Param("email") String email, @Param("password") String password);

    @Select("select * from user where email = #{email} and passwd = #{password} and enable = 1")
    User selectByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Select("select passwd from user where name = #{name} and email = #{email} and enable = 1")
    String checkByUserNameAndEmail(@Param("name") String name, @Param("email") String email);

    @Update("UPDATE user SET name = #{name},phone = #{phone},aboutme = #{aboutme} WHERE email = #{email} and enable = 1")
    int modifyUserByEmail(@Param("name") String name, @Param("phone") String phone, @Param("email") String email, @Param("aboutme") String aboutme);

}
