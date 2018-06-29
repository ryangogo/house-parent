package com.mooc.house.common.vo;

import com.mooc.house.common.model.City;
import com.mooc.house.common.model.Community;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HouseVO {
    private Long id;

    /**
     * 房产名称
     */
    private String name;

    /**
     * 1:销售，2:出租
     */
    private Boolean type;

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
    private Byte state;

    /**
     * 所有城市
     */
    private List<City> cities;

    /**
     * 所有城市
     */
    private List<Community> communities;
}