package com.mooc.house.common.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    /**
     * 主键
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 自我介绍
     */
    private String aboutme;

    /**
     * 密码
     */
    private String passwd;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认密码
     */
    private String confirmPasswd;

    /**
     * 头像图片URL
     */
    private String avatar;

    /**
     *
     */
    private String key;

    /**
     * 1:普通用户，2:房产经纪人
     */
    private Byte type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否启用1：启用，0：停用
     */
    private Byte enable;

    /**
     * 所属经纪机构
     */
    private Integer agencyId;

    /**
     * 经纪机构名称
     */
    private String agencyName;
}