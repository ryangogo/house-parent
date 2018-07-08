package com.mooc.house.common.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class House {
    private Long id;

    /**
     * 房产名称
     */
    private String name;

    /**
     * 1:销售，2:出租
     */
    private String type;

    /**
     * 房屋价格
     */
    private Integer price;

    /**
     * 图片地址
     */
    private String images;

    /**
     * 面积
     */
    private Integer area;

    /**
     * 卧室数量
     */
    private Integer beds;

    /**
     * 卫生间数量
     */
    private Integer baths;

    /**
     * 评级
     */
    private Double rating;

    /**
     * 房产描述
     */
    private String remarks;

    /**
     * 属性
     */
    private String properties;

    /**
     * 户型图
     */
    private String floorPlan;

    /**
     * 标签
     */
    private String tags;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 城市名称
     */
    private Integer cityId;

    /**
     * 小区名称
     */
    private Integer communityId;

    /**
     * 房产地址
     */
    private String address;

    /**
     * 1:上架，2:下架
     */
    private Integer state;

    List<String> imageList = new ArrayList<>();
    List<String> floorPlanList = new ArrayList<>();
    List<String> propertyList = new ArrayList<>();

}