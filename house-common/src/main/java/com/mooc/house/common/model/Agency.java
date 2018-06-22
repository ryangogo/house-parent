package com.mooc.house.common.model;

import lombok.Data;

@Data
public class Agency {
    private Integer id;

    /**
     * 经纪机构名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 手机
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 描述
     */
    private String aboutUs;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 网站
     */
    private String webSite;

}