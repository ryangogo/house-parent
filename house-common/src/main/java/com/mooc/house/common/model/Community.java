package com.mooc.house.common.model;


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

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取城市编码
     *
     * @return city_code - 城市编码
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 设置城市编码
     *
     * @param cityCode 城市编码
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * 获取小区名称
     *
     * @return name - 小区名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置小区名称
     *
     * @param name 小区名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取城市名称
     *
     * @return city_name - 城市名称
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * 设置城市名称
     *
     * @param cityName 城市名称
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}