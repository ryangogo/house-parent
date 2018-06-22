package com.mooc.house.common.model;

import lombok.Data;

import java.util.Date;

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
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取房产名称
     *
     * @return name - 房产名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置房产名称
     *
     * @param name 房产名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取1:销售，2:出租
     *
     * @return type - 1:销售，2:出租
     */
    public Boolean getType() {
        return type;
    }

    /**
     * 设置1:销售，2:出租
     *
     * @param type 1:销售，2:出租
     */
    public void setType(Boolean type) {
        this.type = type;
    }

    /**
     * 获取房屋价格
     *
     * @return price - 房屋价格
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 设置房屋价格
     *
     * @param price 房屋价格
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 获取图片地址
     *
     * @return images - 图片地址
     */
    public String getImages() {
        return images;
    }

    /**
     * 设置图片地址
     *
     * @param images 图片地址
     */
    public void setImages(String images) {
        this.images = images;
    }

    /**
     * 获取面积
     *
     * @return area - 面积
     */
    public Integer getArea() {
        return area;
    }

    /**
     * 设置面积
     *
     * @param area 面积
     */
    public void setArea(Integer area) {
        this.area = area;
    }

    /**
     * 获取卧室数量
     *
     * @return beds - 卧室数量
     */
    public Integer getBeds() {
        return beds;
    }

    /**
     * 设置卧室数量
     *
     * @param beds 卧室数量
     */
    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    /**
     * 获取卫生间数量
     *
     * @return baths - 卫生间数量
     */
    public Integer getBaths() {
        return baths;
    }

    /**
     * 设置卫生间数量
     *
     * @param baths 卫生间数量
     */
    public void setBaths(Integer baths) {
        this.baths = baths;
    }

    /**
     * 获取评级
     *
     * @return rating - 评级
     */
    public Double getRating() {
        return rating;
    }

    /**
     * 设置评级
     *
     * @param rating 评级
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * 获取房产描述
     *
     * @return remarks - 房产描述
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置房产描述
     *
     * @param remarks 房产描述
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取属性
     *
     * @return properties - 属性
     */
    public String getProperties() {
        return properties;
    }

    /**
     * 设置属性
     *
     * @param properties 属性
     */
    public void setProperties(String properties) {
        this.properties = properties;
    }

    /**
     * 获取户型图
     *
     * @return floor_plan - 户型图
     */
    public String getFloorPlan() {
        return floorPlan;
    }

    /**
     * 设置户型图
     *
     * @param floorPlan 户型图
     */
    public void setFloorPlan(String floorPlan) {
        this.floorPlan = floorPlan;
    }

    /**
     * 获取标签
     *
     * @return tags - 标签
     */
    public String getTags() {
        return tags;
    }

    /**
     * 设置标签
     *
     * @param tags 标签
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取城市名称
     *
     * @return city_id - 城市名称
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * 设置城市名称
     *
     * @param cityId 城市名称
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * 获取小区名称
     *
     * @return community_id - 小区名称
     */
    public Integer getCommunityId() {
        return communityId;
    }

    /**
     * 设置小区名称
     *
     * @param communityId 小区名称
     */
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    /**
     * 获取房产地址
     *
     * @return address - 房产地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置房产地址
     *
     * @param address 房产地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取1:上架，2:下架
     *
     * @return state - 1:上架，2:下架
     */
    public Byte getState() {
        return state;
    }

    /**
     * 设置1:上架，2:下架
     *
     * @param state 1:上架，2:下架
     */
    public void setState(Byte state) {
        this.state = state;
    }
}