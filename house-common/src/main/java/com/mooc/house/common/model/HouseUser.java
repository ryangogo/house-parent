package com.mooc.house.common.model;

import lombok.Data;

import java.util.Date;

@Data
public class HouseUser {
    private Long id;

    /**
     * 房屋id
     */
    private Long houseId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 1：售卖，2：收藏
     */
    private Boolean type;

}