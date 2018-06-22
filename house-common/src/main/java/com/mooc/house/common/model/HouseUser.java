package com.mooc.house.common.model;

import java.util.Date;

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
     * 获取房屋id
     *
     * @return house_id - 房屋id
     */
    public Long getHouseId() {
        return houseId;
    }

    /**
     * 设置房屋id
     *
     * @param houseId 房屋id
     */
    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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
     * 获取1：售卖，2：收藏
     *
     * @return type - 1：售卖，2：收藏
     */
    public Boolean getType() {
        return type;
    }

    /**
     * 设置1：售卖，2：收藏
     *
     * @param type 1：售卖，2：收藏
     */
    public void setType(Boolean type) {
        this.type = type;
    }
}