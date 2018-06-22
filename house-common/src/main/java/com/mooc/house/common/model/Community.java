package com.mooc.house.common.model;

import lombok.Data;

@Data
public class Community {
    private Integer id;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 小区名称
     */
    private String name;

    /**
     * 城市名称
     */
    private String cityName;

}